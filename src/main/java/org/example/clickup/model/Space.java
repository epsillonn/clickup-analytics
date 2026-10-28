package org.example.clickup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Space(
        String id,
        String name,
        @JsonProperty("private") Boolean isPrivate,
        String color,
        String avatar,
        Boolean adminCanManage,
        Boolean archived,
        List<WorkspaceMember> members,
        List<Status> statuses,
        Boolean multipleAssignees,
        Features features) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Features(
            DueDates dueDates,
            Toggle timeTracking,
            Toggle tags,
            Toggle timeEstimates,
            Toggle checklists,
            Toggle customFields,
            Toggle remapDependencies,
            Toggle dependencyWarning,
            Toggle portfolios) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record DueDates(Boolean enabled, Boolean startDate, Boolean remapDueDates, Boolean remapClosedDueDate) {}

    public record Toggle(Boolean enabled) {}
}
