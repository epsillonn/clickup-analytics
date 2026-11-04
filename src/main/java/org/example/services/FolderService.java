package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Client.Client;
import org.example.dto.FolderResponseDto;
import org.example.entity.Folder;
import org.example.entity.Space;
import org.example.mapper.FolderMapper;
import org.example.repository.FolderRepository;
import org.example.repository.SpaceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final SpaceRepository spaceRepository;
    private final Client client;
    private final FolderMapper folderMapper;
    private final ListService listService;

    @Transactional
    public void syncFolders(String spaceId, String userAccessToken) {

        FolderResponseDto response = client.getFolders(spaceId, userAccessToken);

        if (response == null || response.folders() == null) {
            return;
        }

        Space space = spaceRepository
                .findById(spaceId)
                .orElseThrow(() -> new RuntimeException("Space bulunamadı: " + spaceId));

        for (FolderResponseDto.FolderDto folderDto : response.folders()) {

            Folder folder = folderRepository.findById(folderDto.id()).orElseGet(Folder::new);

            folderMapper.updateEntityFromDto(folderDto, folder);
            folder.setSpace(space);

            folderRepository.save(folder);

            if (folderDto.lists() != null) {
                for (FolderResponseDto.ListDto listDto : folderDto.lists()) {
                    listService.saveFolderList(listDto, folder, space, userAccessToken);
                }
            }
        }
    }
}
