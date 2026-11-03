package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.entity.Space;
import org.example.entity.Workspace;
import org.example.mapper.SpaceMapper;
import org.example.Client.Client;
import org.example.dto.SpaceResponseDto;
import org.example.repository.SpaceRepository;
import org.example.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final Client client;
    private final WorkspaceRepository workspaceRepository;
    private final SpaceMapper spaceMapper;

    @Transactional
    public void syncSpaces(String workspaceId, String userAccessToken) {

        SpaceResponseDto response = client.getSpaces(workspaceId, userAccessToken);

        if (response == null || response.spaces() == null) {
            return;
        }

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace bulunamadı: " + workspaceId));

        for (SpaceResponseDto.SpaceDto spaceDto : response.spaces()) {

            Space space = spaceRepository.findById(spaceDto.id())
                    .orElseGet(Space::new);

            spaceMapper.updateEntityFromDto(spaceDto, space);
            if (spaceDto.statuses() != null) {
                space.setStatuses(spaceDto.statuses());
            }
            space.setWorkspace(workspace);
            spaceRepository.save(space);
        }
    }

}
