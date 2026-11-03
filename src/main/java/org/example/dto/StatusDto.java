package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatusDto(
        String id,
        String status,
        @JsonProperty("orderindex")
        Integer orderIndex,
        String color,
        String type
) {}