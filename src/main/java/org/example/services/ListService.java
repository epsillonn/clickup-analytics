package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Client.Client;
import org.example.dto.FolderResponseDto;
import org.example.dto.ListResponseDto;
import org.example.dto.StatusDto;
import org.example.entity.Folder;
import org.example.entity.Lists;
import org.example.entity.Space;
import org.example.mapper.ListMapper;
import org.example.repository.ListRepository;
import org.example.repository.SpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListService {

    private final Client client;
    private final ListRepository listRepository;
    private final SpaceRepository spaceRepository;
    private final ListMapper listMapper;


    @Transactional
    public void syncSpaceLists(String spaceId, String userAccessToken) {
        ListResponseDto response;

        try {
            log.info("Space ID: {} için klasörsüz listeler ClickUp API'den isteniyor...", spaceId);
            response = client.getFolderlessLists(spaceId, userAccessToken);
        } catch (HttpClientErrorException.Unauthorized e) {
            log.warn("Space ID: {} listeleri için yetki bulunamadı (401 Unauthorized / OAUTH_192). Atlanıyor. Hata: {}",
                    spaceId, e.getMessage());
            return;
        } catch (Exception e) {
            log.error("Space ID: {} listeleri çekilirken hata oluştu: {}", spaceId, e.getMessage());
            return;
        }

        if (response == null || response.lists() == null || response.lists().isEmpty()) {
            log.info("Space ID: {} altında klasörsüz liste bulunamadı.", spaceId);
            return;
        }

        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new RuntimeException("Space veritabanında bulunamadı: " + spaceId));

        for (ListResponseDto.ListDto listDto : response.lists()) {
            Lists list = listRepository.findById(listDto.id())
                    .orElseGet(Lists::new);

            listMapper.updateEntityFromDto(listDto, list);

            list.setSpace(space);
            list.setFolder(null); // Klasörsüz liste
            list.setParentTaskCount(listDto.taskCount());
            list.setStatuses(resolveStatuses(
                    listDto.id(), listDto.overrideStatuses(), listDto.statuses(), space, userAccessToken));

            listRepository.save(list);
        }

        log.info("Space ID: {} için {} adet klasörsüz liste başarıyla senkronize edildi.", spaceId, response.lists().size());
    }

    public void saveFolderList(
            FolderResponseDto.ListDto listDto,
            Folder folder,
            Space space,
            String accessToken) {
        Lists list = listRepository.findById(listDto.id()).orElseGet(Lists::new);
        listMapper.updateEntityFromDto(listDto, list);
        list.setFolder(folder);
        list.setSpace(space);
        list.setParentTaskCount(listDto.taskCount());
        list.setStatuses(resolveStatuses(
                listDto.id(), listDto.overrideStatuses(), listDto.statuses(), space, accessToken));
        listRepository.save(list);
    }

    private List<StatusDto> resolveStatuses(
            String listId,
            Boolean overrideStatuses,
            List<StatusDto> listStatuses,
            Space space,
            String accessToken) {
        if (!Boolean.TRUE.equals(overrideStatuses)) {
            return space.getStatuses() == null ? List.of() : space.getStatuses();
        }

        if (listStatuses != null && !listStatuses.isEmpty()) {
            return listStatuses;
        }

        log.info("Liste {} özel statüleri yanıt içinde yok; liste detayı isteniyor.", listId);
        ListResponseDto.ListDto details = client.getList(listId, accessToken);
        if (details == null || details.statuses() == null) {
            throw new IllegalStateException("ClickUp liste detayında statüler bulunamadı: " + listId);
        }
        return details.statuses();
    }
}
