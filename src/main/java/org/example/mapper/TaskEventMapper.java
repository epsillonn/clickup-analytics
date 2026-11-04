package org.example.mapper;

import org.example.entity.TaskEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.example.dto.TaskEventResponseDto;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskEventMapper {
    @Mapping(source = "task.id", target = "taskId")
    TaskEventResponseDto toDto(TaskEvent entity);

    @Mapping(source = "taskId", target = "task.id")
    TaskEvent toEntity(TaskEventResponseDto dto);
}
