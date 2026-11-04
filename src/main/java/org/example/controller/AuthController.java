package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AuthResponseDto;
import org.example.services.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${app.frontend-url:http://localhost/dashboard}")
    private String frontendUrl;

    @GetMapping(
            value = {"/api/auth/callback", "/"},
            params = "code")
    public ResponseEntity<Void> handleOAuthCallback(@RequestParam("code") String code, HttpServletRequest request) {

        log.info(
                "HTTP GET {} OAuth callback isteği alındı. Remote IP: {}",
                request.getRequestURI(),
                request.getRemoteAddr());

        // 1. OAuth akışı, senkronizasyon ve JWT üretimini çalıştır
        AuthResponseDto response = authService.oauthCallback(code, request);

        // 2. Kullanıcının ilk/varsayılan Workspace ID'si
        String defaultWorkspaceId =
                (response.workspaces() != null && !response.workspaces().isEmpty())
                        ? response.workspaces().get(0).id()
                        : "";

        String role = (response.user().getRole() != null) ? response.user().getRole() : "DEVELOPER";

        // 3. Frontend Dashboard için yönlendirme URL'ini güvenli şekilde inşa et
        String targetUrl = UriComponentsBuilder.fromUriString(frontendUrl)
                .queryParam("token", response.accessToken())
                .queryParam("userId", response.user().getId())
                .queryParam("username", response.user().getUsername())
                .queryParam("role", role)
                .queryParam("workspaceId", defaultWorkspaceId)
                .build()
                .toUriString();

        log.info("Kullanıcı Frontend panosuna yönlendiriliyor: {}", targetUrl);

        // 4. HTTP 302 Found ile tarayıcıyı Frontend Dashboard'a yönlendir
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(targetUrl))
                .build();
    }
}
