package org.example.clickup.api;

import java.util.List;
import org.example.clickup.model.Task;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TasksResponse(List<Task> tasks, Boolean lastPage) {}
