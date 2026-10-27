package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * A task as returned by Get Task, Get Tasks and Get Filtered Team Tasks. Dates are Unix epoch milliseconds, and
 * {@code timeEstimate} and {@code timeSpent} are milliseconds. {@code project} is the task's Folder; ClickUp still
 * sends it under its legacy name next to {@code folder}.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Task(
        String id,
        String customId,
        Integer customItemId,
        String name,
        String textContent,
        String description,
        String markdownDescription,
        Status status,
        String orderindex,
        Long dateCreated,
        Long dateUpdated,
        Long dateClosed,
        Long dateDone,
        Boolean archived,
        User creator,
        List<User> assignees,
        List<JsonNode> groupAssignees,
        List<User> watchers,
        List<Checklist> checklists,
        List<Tag> tags,
        String parent,
        String topLevelParent,
        Priority priority,
        Long dueDate,
        Long startDate,
        Double points,
        Long timeEstimate,
        Long timeSpent,
        List<CustomField> customFields,
        List<Dependency> dependencies,
        List<JsonNode> linkedTasks,
        List<HierarchyRef> locations,
        String teamId,
        String url,
        String permissionLevel,
        HierarchyRef list,
        HierarchyRef project,
        HierarchyRef folder,
        HierarchyRef space,
        List<Task> subtasks,
        List<Attachment> attachments) {}
