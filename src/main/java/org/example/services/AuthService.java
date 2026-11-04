package org.example.services;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Client.Client;
import org.example.config.JwtProperties;
import org.example.dto.AuthResponseDto;
import org.example.dto.OAuthDto;
import org.example.dto.UserDto;
import org.example.entity.LoginLog;
import org.example.entity.User;
import org.example.entity.UserTokens;
import org.example.entity.Workspace;
import org.example.mapper.UserMapper;
import org.example.repository.LoginLogRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserTokensRepository;
import org.example.repository.WorkspaceMemberRepository;
import org.example.repository.WorkspaceRepository;
import org.example.security.JwtService;
import org.springframework.stereotype.Service;
import ua_parser.Parser;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Parser UA_PARSER = new Parser();

    private final Client client;
    private final UserRepository userRepository;
    private final UserTokensRepository userTokensRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final LoginLogRepository loginLogRepository;
    private final UserMapper userMapper;
    private final WorkspaceService workspaceService;
    private final WorkspaceVerificationService workspaceVerificationService;
    private final ClickUpFullSyncService clickUpFullSyncService;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthResponseDto oauthCallback(String code, HttpServletRequest request) {
        log.info("ClickUp OAuth callback tetiklendi. Gelen Code: {}", code);

        log.info("ClickUp API'sine token isteği atılıyor...");
        OAuthDto.Response tokenResponse = client.getAccessToken(code);
        log.info("Access Token başarıyla alındı.");

        log.info("ClickUp yetkili kullanıcı profili isteniyor...");
        UserDto clickupUser = client.getAuthorizedUser(tokenResponse.accessToken());
        if (clickupUser == null) {
            log.error("ClickUp'tan kullanıcı bilgisi çekilemedi!");
            throw new RuntimeException("ClickUp kullanıcı bilgisi alınamadı.");
        }
        log.info(
                "Kullanıcı profili alındı: ID={}, Username={}, Email={}",
                clickupUser.id(),
                clickupUser.username(),
                clickupUser.email());

        User user = userRepository.findById(clickupUser.id()).orElseGet(() -> {
            log.info("Yeni kullanıcı veritabanına ekleniyor (ID: {})...", clickupUser.id());
            return User.builder().id(clickupUser.id()).build();
        });

        userMapper.updateEntityFromDto(clickupUser, user);
        userRepository.save(user);
        log.info("Kullanıcı bilgileri 'users' tablosuna kaydedildi.");

        saveLoginLog(user, request);

        java.util.List<String> workspaceIds = workspaceService.syncWorkspaces(tokenResponse.accessToken());
        java.util.List<String> authorizedWorkspaceIds = workspaceIds.stream()
                .filter(workspaceId ->
                        workspaceVerificationService.verifyWorkspaceAccess(workspaceId, tokenResponse.accessToken()))
                .toList();

        userTokensRepository.deleteByUserId(user.getId());

        // 6. Token'ı yalnızca erişimi doğrulanan workspace'lerle eşleştir
        for (String workspaceId : workspaceIds) {
            if (!authorizedWorkspaceIds.contains(workspaceId)) {
                continue;
            }

            Workspace workspace = workspaceRepository
                    .findById(workspaceId)
                    .orElseThrow(() -> new IllegalStateException(
                            "ClickUp tarafından dönen workspace veritabanında bulunamadı: " + workspaceId));
            UserTokens tokenEntity = userTokensRepository
                    .findByUserIdAndWorkspaceId(user.getId(), workspaceId)
                    .orElseGet(UserTokens::new);
            tokenEntity.setUser(user);
            tokenEntity.setWorkspace(workspace);
            tokenEntity.setAccessToken(tokenResponse.accessToken());
            tokenEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            userTokensRepository.save(tokenEntity);

            try {
                clickUpFullSyncService.syncWorkspaceData(
                        String.valueOf(user.getId()), workspaceId, tokenResponse.accessToken());
            } catch (RuntimeException e) {
                log.error(
                        "OAuth girişi tamamlandı ancak workspace senkronizasyonu başarısız oldu. "
                                + "User ID: {}, Workspace ID: {}",
                        user.getId(),
                        workspaceId,
                        e);
            }
        }

        java.util.List<AuthResponseDto.WorkspaceInfo> userWorkspaces =
                workspaceMemberRepository.findByUserId(user.getId()).stream()
                        .filter(wm -> wm.getWorkspace() != null)
                        .filter(wm -> authorizedWorkspaceIds.contains(
                                wm.getWorkspace().getId()))
                        .map(wm -> new AuthResponseDto.WorkspaceInfo(
                                wm.getWorkspace().getId(), wm.getWorkspace().getName()))
                        .distinct()
                        .toList();

        // İlk/varsayılan workspace ID (JWT içine gömmek için)
        String defaultWorkspaceId =
                userWorkspaces.isEmpty() ? null : userWorkspaces.get(0).id();

        // 8. Bizim sistem için JWT üret (userId, username, email ve varsayılan workspaceId ile)
        String jwtToken = jwtService.generateToken(user, defaultWorkspaceId);
        log.info(
                "Kullanıcı için JWT başarıyla üretildi. User ID: {}, Default Workspace ID: {}, Toplam Workspace: {}",
                user.getId(),
                defaultWorkspaceId,
                userWorkspaces.size());

        long expiresIn = jwtProperties.expirationMs() <= 0 ? -1 : jwtProperties.expirationMs() / 1000;
        log.info("OAuth akışı başarıyla tamamlandı. Kullanıcı ID: {}", user.getId());
        return AuthResponseDto.of(jwtToken, expiresIn, user, userWorkspaces);
    }

    private void saveLoginLog(User user, HttpServletRequest request) {
        String clientIp = extractClientIp(request);
        String userAgentHeader = request.getHeader("User-Agent");

        LoginLog logEntity = new LoginLog();
        logEntity.setUser(user);
        logEntity.setIpAddress(clientIp);

        if (userAgentHeader != null && !userAgentHeader.isBlank()) {
            ua_parser.Client parsedAgent = UA_PARSER.parse(userAgentHeader);
            logEntity.setBrowser(parsedAgent.userAgent.family);
            logEntity.setOperatingSystem(parsedAgent.os.family);
            logEntity.setDeviceModel(parsedAgent.device.family);

            log.info(
                    "User-Agent ayrıştırıldı: Browser={}, OS={}, Device={}, IP={}",
                    parsedAgent.userAgent.family,
                    parsedAgent.os.family,
                    parsedAgent.device.family,
                    clientIp);
        } else {
            logEntity.setBrowser("Unknown");
            logEntity.setOperatingSystem("Unknown");
            logEntity.setDeviceModel("Unknown");
            log.warn("User-Agent başlığı boş geldi. Bilinmeyen cihaz olarak kaydediliyor.");
        }

        logEntity.setLoggedInAt(new Timestamp(System.currentTimeMillis()));
        loginLogRepository.save(logEntity);
        log.info("Oturum kaydı 'login_logs' tablosuna yazıldı.");
    }

    private String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            return request.getRemoteAddr();
        }
        return ip.split(",")[0].trim();
    }
}
