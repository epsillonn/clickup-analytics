package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceVerificationService {

    private final RestClient clickUpRestClient;

    public boolean verifyWorkspaceAccess(String workspaceId, String accessToken) {
        try {
            return clickUpRestClient
                            .get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/team/{workspaceId}/space")
                                    .queryParam("archived", false)
                                    .build(workspaceId))
                            .header("Authorization", accessToken)
                            .retrieve()
                            .toBodilessEntity()
                            .getStatusCode()
                    == HttpStatus.OK;
        } catch (HttpClientErrorException.Unauthorized e) {
            log.warn("ClickUp token is not authorized for workspace {}.", workspaceId);
            return false;
        }
    }
}
