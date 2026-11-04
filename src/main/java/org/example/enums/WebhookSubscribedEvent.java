package org.example.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum WebhookSubscribedEvent {
    TASK_CREATED("taskCreated"),
    TASK_UPDATED("taskUpdated"),
    TASK_DELETED("taskDeleted"),
    TASK_STATUS_UPDATED("taskStatusUpdated"),
    TASK_PRIORITY_UPDATED("taskPriorityUpdated"),
    TASK_ASSIGNEE_UPDATED("taskAssigneeUpdated"),
    TASK_DUE_DATE_UPDATED("taskDueDateUpdated"),
    TASK_TIME_ESTIMATE_UPDATED("taskTimeEstimateUpdated"),
    TASK_TIME_TRACKED_UPDATED("taskTimeTrackedUpdated"),
    TASK_MOVED("taskMoved"),
    TASK_TAG_UPDATED("taskTagUpdated"),


    LIST_CREATED("listCreated"),
    LIST_UPDATED("listUpdated"),
    LIST_DELETED("listDeleted"),

    FOLDER_CREATED("folderCreated"),
    FOLDER_UPDATED("folderUpdated"),
    FOLDER_DELETED("folderDeleted"),

    SPACE_CREATED("spaceCreated"),
    SPACE_UPDATED("spaceUpdated"),
    SPACE_DELETED("spaceDeleted"),

    UNSUPPORTED("unsupported");

    private final String eventName;



    WebhookSubscribedEvent(String eventName) {
        this.eventName = eventName;
    }
    @JsonValue

    public String getEventName() {
        return eventName;
    }
    @JsonCreator
    public static WebhookSubscribedEvent fromEventName(String eventName) {
        return Arrays.stream(values())
                .filter(type -> type.getEventName().equals(eventName))
                .findFirst()
                .orElse(UNSUPPORTED);

    }
}
