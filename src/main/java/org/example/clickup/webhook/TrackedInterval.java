package org.example.clickup.webhook;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TrackedInterval(String id, Long start, Long end, Long time, String source, Long dateAdded) {}
