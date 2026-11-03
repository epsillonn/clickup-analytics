package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.validateToken(token)) {
                Long userId = jwtService.extractUserId(token);
                String email = jwtService.extractEmail(token);
                String username = jwtService.extractUsername(token);
                String workspaceId = jwtService.extractWorkspaceId(token);
                String role = jwtService.extractRole(token);

                // Controller ve servislerde doğrudan erişim için request attribute'larına eklenir
                request.setAttribute("currentUserId", userId);
                request.setAttribute("currentUserEmail", email);
                request.setAttribute("currentUsername", username);
                request.setAttribute("currentWorkspaceId", workspaceId);
                request.setAttribute("currentUserRole", role);

                log.debug("JWT doğrulandı. User ID: {}, Workspace ID: {}, Role: {}", userId, workspaceId, role);
            } else {
                log.warn("Geçersiz JWT token ile istek yapıldı: {}", request.getRequestURI());
            }
        }

        filterChain.doFilter(request, response);
    }
}
