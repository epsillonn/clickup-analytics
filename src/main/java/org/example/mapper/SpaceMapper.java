package org.example.mapper;

import org.example.dto.SpaceResponseDto;
import org.example.entity.Space;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpaceMapper {

    @Mapping(target = "workspace", ignore = true)
    Space toEntity(SpaceResponseDto.SpaceDto dto);

    SpaceResponseDto.SpaceDto toDto(Space entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "workspace", ignore = true)
    void updateEntityFromDto(SpaceResponseDto.SpaceDto dto, @MappingTarget Space entity);
}
