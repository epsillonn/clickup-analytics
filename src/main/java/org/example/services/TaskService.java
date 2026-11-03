package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Client.Client;
import org.example.dto.TaskResponseDto;
import org.example.dto.UserDto;
import org.example.entity.Lists;
import org.example.entity.Task;
import org.example.entity.TaskAssignee;
import org.example.mapper.TaskMapper;
import org.example.repository.ListRepository;
import org.example.repository.TaskAssigneeRepository;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskAssigneeRepository taskAssigneeRepository;
    private final ListRepository listRepository;
    private final UserRepository userRepository;
    private final Client client;
    private final TaskMapper taskMapper;


    @Transactional
    public void syncListTasks(String listId, String userAccessToken) {
        TaskResponseDto response;

        try {
            log.info("List ID: {} için görevler ClickUp API'den isteniyor...", listId);
            response = client.getTasks(listId, userAccessToken);

        } catch (HttpClientErrorException.Unauthorized e) {
            // 401 OAUTH_192 hatasını yakalar, loglar ve bu listeyi atlayarak transaction rollback'ini engeller
            log.warn("List ID: {} için yetki bulunamadı (401 Unauthorized / OAUTH_192). Liste atlanıyor. Hata: {}",
                    listId, e.getMessage());
            return;
        } catch (Exception e) {
            // tüm senkronizasyonu düşürmez
            log.error("List ID: {} görevleri çekilirken hata oluştu. Liste atlanıyor. Hata: {}",
                    listId, e.getMessage());
            return;
        }

        if (response == null) {
            log.warn("List ID: {} görev yanıtı boş geldi; görev metrikleri güncellenmedi.", listId);
            return;
        }

        Lists list = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Liste veritabanında bulunamadı: " + listId));
        List<TaskResponseDto.TaskDto> tasks = response.tasks() == null ? List.of() : response.tasks();

        for (TaskResponseDto.TaskDto taskDto : tasks) {
            try {
                Task task = taskRepository.findById(taskDto.id())
                        .orElseGet(Task::new);

                // Alanların DTO'dan Entity'ye aktarımı (MapStruct)
                taskMapper.updateEntityFromDto(taskDto, task);

                // İlişkilerin kurulması
                task.setLists(list);
                task.setWorkspace(list.getSpace() != null ? list.getSpace().getWorkspace() : null);

                // Creator (Long ID dönüşümü)
                if (taskDto.creator() != null && taskDto.creator().id() != null) {
                    try {
                        Long creatorId = Long.valueOf(taskDto.creator().id().toString());
                        userRepository.findById(creatorId).ifPresent(task::setCreator);
                    } catch (NumberFormatException e) {
                        log.warn("Task Creator ID sayıya dönüştürülemedi: {}", taskDto.creator().id());
                    }
                }

                // Parent Task ilişkileri
                if (taskDto.parent() != null && !taskDto.parent().isBlank()) {
                    taskRepository.findById(taskDto.parent()).ifPresent(task::setParent);
                }
                if (taskDto.topLevelParent() != null && !taskDto.topLevelParent().isBlank()) {
                    taskRepository.findById(taskDto.topLevelParent()).ifPresent(task::setTopLevelParent);
                }

                Task savedTask = taskRepository.save(task);

                syncTaskAssignees(savedTask, taskDto.assignees());

            } catch (Exception e) {
                log.error("Task ID: {} kaydedilirken hata oluştu, görev atlandı: {}", taskDto.id(), e.getMessage());
            }
        }
        int parentTaskCount = list.getTaskCount() == null ? 0 : list.getTaskCount();
        int totalTaskCount = tasks.size();
        list.setParentTaskCount(list.getTaskCount());
        list.setTotalTaskCount(totalTaskCount);
        list.setSubtaskCount(Math.max(0, totalTaskCount - parentTaskCount));
        listRepository.save(list);

        log.info("List ID: {} için {} görev senkronize edildi (parent: {}, subtask: {}).",
                listId, totalTaskCount, list.getParentTaskCount(), list.getSubtaskCount());
    }

    private void syncTaskAssignees(Task task, List<UserDto> assigneesDto) {
        if (assigneesDto == null || assigneesDto.isEmpty()) {
            return;
        }

        List<TaskAssignee> existingAssignees = taskAssigneeRepository.findByTaskId(task.getId());
        if (!existingAssignees.isEmpty()) {
            taskAssigneeRepository.deleteAll(existingAssignees);
        }

        for (UserDto userDto : assigneesDto) {
            if (userDto.id() != null) {
                try {
                    Long userId = Long.valueOf(userDto.id().toString());
                    userRepository.findById(userId).ifPresent(user -> {
                        TaskAssignee taskAssignee = TaskAssignee.builder()
                                .task(task)
                                .user(user)
                                .build();
                        taskAssigneeRepository.save(taskAssignee);
                    });
                } catch (NumberFormatException e) {
                    log.warn("Assignee User ID sayıya dönüştürülemedi: {}", userDto.id());
                }
            }
        }
    }
}
