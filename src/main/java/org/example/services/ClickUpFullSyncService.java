package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Lists;
import org.example.entity.Space;
import org.example.repository.ListRepository;
import org.example.repository.SpaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClickUpFullSyncService {

    private final SpaceService spaceService;
    private final FolderService folderService;
    private final ListService listService;
    private final TaskService taskService;

    private final SpaceRepository spaceRepository;
    private final ListRepository listRepository;

    public void syncWorkspaceData(String userId, String workspaceId, String accessToken) {
        log.info("User {} için workspace {} ClickUp senkronizasyonu başlatılıyor.", userId, workspaceId);

        try {
            spaceService.syncSpaces(workspaceId, accessToken);
            List<Space> workspaceSpaces = spaceRepository.findByWorkspaceId(workspaceId);

            for (Space space : workspaceSpaces) {
                folderService.syncFolders(space.getId(), accessToken);
                listService.syncSpaceLists(space.getId(), accessToken);
            }
            log.info("Workspace {} için klasör ve listeler senkronize edildi.", workspaceId);

            for (Space space : workspaceSpaces) {
                for (Lists list : listRepository.findBySpaceId(space.getId())) {
                    taskService.syncListTasks(list.getId(), accessToken);
                }
            }

            log.info("Workspace {} ClickUp verileri başarıyla senkronize edildi.", workspaceId);

        } catch (Exception e) {
            log.error("Workspace {} ClickUp senkronizasyonu başarısız oldu.", workspaceId, e);
            throw new RuntimeException("ClickUp workspace senkronizasyonu tamamlanamadı: " + workspaceId, e);
        }
    }
}