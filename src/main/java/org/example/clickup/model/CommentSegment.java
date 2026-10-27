package org.example.clickup.model;

import tools.jackson.databind.JsonNode;

public record CommentSegment(String text, String type, JsonNode attributes) {}
