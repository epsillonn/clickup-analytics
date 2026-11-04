package org.example.mapper;

import org.example.dto.WorkspaceResponseDto;
import org.example.entity.Workspace;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkspaceMapper {

    Workspace toEntity(WorkspaceResponseDto.WorkspaceDto dto);

    @Mapping(target = "members", ignore = true)
    WorkspaceResponseDto.WorkspaceDto toDto(Workspace entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(WorkspaceResponseDto.WorkspaceDto dto, @MappingTarget Workspace entity);
}
