package org.example.dto;

import tools.jackson.databind.JsonNode;

public record HistoryItem(
        String id,
        Integer type,
        Long date,
        String field,
        String parentId,
        JsonNode data,
        JsonNode source,
        JsonNode user,
        JsonNode before,
        JsonNode after) {}
