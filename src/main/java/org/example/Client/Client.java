package org.example.Client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.config.ClickUpProperties;
import org.example.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Client {

    private final RestClient restClient;
    private final ClickUpProperties properties;

    public record UserWrapper(@JsonProperty("user") UserDto user) {}

    public Client(RestClient restClient, ClickUpProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    // --- Auth Endpoints ---

    public OAuthDto.Response getAccessToken(String code) {
        return restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("client_id", properties.api().clientId())
                        .queryParam("client_secret", properties.api().clientSecret())
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .body(OAuthDto.Response.class);
    }

    public UserDto getAuthorizedUser(String accessToken) {
        UserWrapper wrapper = restClient
                .get()
                .uri("/user")
                .header("Authorization", accessToken)
                .retrieve()
                .body(UserWrapper.class);

        return wrapper != null ? wrapper.user() : null;
    }

    // --- Data Sync Endpoints ---

    public WorkspaceResponseDto getWorkspaces(String accessToken) {
        return restClient
                .get()
                .uri(properties.api().endpoints().teams())
                .header("Authorization", accessToken)
                .retrieve()
                .body(WorkspaceResponseDto.class);
    }

    public SpaceResponseDto getSpaces(String workspaceId, String accessToken) {
        return restClient
                .get()
                .uri(properties.api().endpoints().spaces(), workspaceId)
                .header("Authorization", accessToken)
                .retrieve()
                .body(SpaceResponseDto.class);
    }

    public FolderResponseDto getFolders(String spaceId, String accessToken) {
        return restClient
                .get()
                .uri(properties.api().endpoints().folders(), spaceId)
                .header("Authorization", accessToken)
                .retrieve()
                .body(FolderResponseDto.class);
    }

    public ListResponseDto getFolderlessLists(String spaceId, String accessToken) {
        return restClient
                .get()
                .uri(properties.api().endpoints().spaceList(), spaceId)
                .header("Authorization", accessToken)
                .retrieve()
                .body(ListResponseDto.class);
    }

    public ListResponseDto.ListDto getList(String listId, String accessToken) {
        return restClient
                .get()
                .uri("/list/{listId}", listId)
                .header("Authorization", accessToken)
                .retrieve()
                .body(ListResponseDto.ListDto.class);
    }

    public ListResponseDto getFolderLists(String folderId, String accessToken) {
        return restClient
                .get()
                .uri("/folder/{folderId}/list", folderId)
                .header("Authorization", accessToken)
                .retrieve()
                .body(ListResponseDto.class);
    }

    public TaskResponseDto getTasks(String listId, String accessToken) {
        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/list/{listId}/task")
                        .queryParam("subtasks", true)
                        .queryParam("include_subtasks", true)
                        .queryParam("archived", false)
                        .build(listId))
                .header("Authorization", accessToken)
                .retrieve()
                .body(TaskResponseDto.class);
    }
}
