package org.example.mapper;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "roleKey", ignore = true)
    @Mapping(target = "dateInvited", ignore = true)
    @Mapping(target = "dateJoined", ignore = true)
    UserDto toDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget User entity);
}
