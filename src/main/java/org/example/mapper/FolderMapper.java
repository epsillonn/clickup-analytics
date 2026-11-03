package org.example.mapper;

import org.example.dto.FolderResponseDto;
import org.example.entity.Folder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FolderMapper {

    @Mapping(target = "space", ignore = true)
    Folder toEntity(FolderResponseDto.FolderDto dto);

    FolderResponseDto.FolderDto toDto(Folder entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "space", ignore = true)
    void updateEntityFromDto(FolderResponseDto.FolderDto dto, @MappingTarget Folder entity);
}
