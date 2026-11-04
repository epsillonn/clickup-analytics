package org.example.mapper;

import org.example.dto.WebhookResponseDto;
import org.example.entity.Webhook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebhookMapper {
    WebhookResponseDto toDto(Webhook entity);

    Webhook toEntity(WebhookResponseDto dto);
}
