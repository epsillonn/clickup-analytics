package org.example.mapper;

import org.example.dto.FolderResponseDto;
import org.example.dto.ListResponseDto;
import org.example.entity.Lists;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListMapper {

    // 1. Folder içinden gelen listeler için:
    @Mapping(target = "space", ignore = true)
    @Mapping(target = "folder", ignore = true)
    Lists toEntity(FolderResponseDto.ListDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "space", ignore = true)
    @Mapping(target = "folder", ignore = true)
    void updateEntityFromDto(FolderResponseDto.ListDto dto, @MappingTarget Lists entity);

    // 2. Space altından (Folder'sız) gelen listeler için:
    @Mapping(target = "space", ignore = true)
    @Mapping(target = "folder", ignore = true)
    Lists toEntity(ListResponseDto.ListDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "space", ignore = true)
    @Mapping(target = "folder", ignore = true)
    void updateEntityFromDto(ListResponseDto.ListDto dto, @MappingTarget Lists entity);
}
