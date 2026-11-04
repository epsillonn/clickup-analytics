package org.example.mapper;

import org.example.dto.HierarchyEventResponseDto;
import org.example.entity.HierarchyEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HierarchyEventMapper {
    HierarchyEventResponseDto toDto(HierarchyEvent entity);
    HierarchyEvent toEntity(HierarchyEventResponseDto dto);
}
