package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SpaceResponseDto(
        @JsonProperty("spaces") List<SpaceDto> spaces
) {
    public record SpaceDto(
            String id,
            String name,
            @JsonProperty("private") Boolean isPrivate,
            Boolean archived,
            List<StatusDto> statuses
    ){}


}
