package org.example.clickup.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.example.clickup.ClickUpFixtures;
import org.example.clickup.model.Task;
import org.example.clickup.model.TaskTimeInStatus;
import org.example.clickup.model.TimeEntry;
import org.example.clickup.webhook.WebhookEventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@JsonTest
class ClickUpApiResponsesTest {
    @Autowired
    private JsonMapper mapper;

    private <T> T read(String name, Class<T> type) {
        return ClickUpFixtures.read(mapper, "api/" + name + ".json", type);
    }

    @Test
    void readsAuthorizedUser() {
        var user = read("authorized-user", AuthorizedUserResponse.class).user();

        assertThat(user.id()).isEqualTo(123L);
        assertThat(user.profilePicture()).isEqualTo("https://clickup.com/avatar.jpg");
    }

    @Test
    void readsWorkspaces() {
        var workspace = read("workspaces", WorkspacesResponse.class).teams().getFirst();

        assertThat(workspace.id()).isEqualTo("1234");
        assertThat(workspace.members().getFirst().user().id()).isEqualTo(123L);
    }

    @Test
    void readsSpacesWithFeatures() {
        var space = read("spaces", SpacesResponse.class).spaces().getFirst();

        assertThat(space.isPrivate()).isFalse();
        assertThat(space.statuses()).extracting("type").containsExactly("open", "closed");
        assertThat(space.features().checklists().enabled()).isTrue();
        assertThat(space.features().dueDates().remapClosedDueDate()).isFalse();
    }

    @Test
    void readsFoldersWithStringTaskCount() {
        var folders = read("folders", FoldersResponse.class).folders();

        assertThat(folders.getFirst().taskCount()).isZero();
        assertThat(folders.getFirst().space().id()).isEqualTo("789");
        assertThat(folders.get(1).parentFolder()).isEqualTo("457");
    }

    @Test
    void readsFolderlessLists() {
        var list = read("folderless-lists", ListsResponse.class).lists().getFirst();

        assertThat(list.status().hideLabel()).isTrue();
        assertThat(list.priority().priority()).isEqualTo("high");
        assertThat(list.taskCount()).isNull();
        assertThat(list.dueDate()).isEqualTo(1567780450202L);
        assertThat(list.folder().hidden()).isTrue();
        assertThat(list.space().id()).isEqualTo("789");
    }

    @Test
    void readsCustomFieldDefinitions() {
        var field = read("custom-fields", CustomFieldsResponse.class).fields().getFirst();

        assertThat(field.type()).isEqualTo("text");
        assertThat(field.dateCreated()).isEqualTo(1577378759142L);
        assertThat(field.typeConfig().isEmpty()).isTrue();
        assertThat(field.value()).isNull();
    }

    @Test
    void readsFilteredTeamTasks() {
        Task task = read("filtered-team-tasks", TasksResponse.class).tasks().getFirst();

        assertThat(task.dateCreated()).isEqualTo(1508369194377L);
        assertThat(task.status().type()).isEqualTo("open");
        assertThat(task.creator().id()).isEqualTo(123L);
        assertThat(task.assignees()).extracting("username").containsExactly("John Doe");
        assertThat(task.priority().priority()).isEqualTo("urgent");
        assertThat(task.checklists().getFirst().items().getFirst().resolved()).isFalse();
        assertThat(task.tags().getFirst().tagFg()).isEqualTo("#000000");
        assertThat(task.customFields().getFirst().type()).isEqualTo("location");
        assertThat(task.points()).isEqualTo(3.0);
        assertThat(task.project().name()).isEqualTo("Folder");
    }

    @Test
    void readsCommentsMembersAndTags() {
        var comment = read("task-comments", CommentsResponse.class).comments().getFirst();
        assertThat(comment.commentText()).isEqualTo("Task comment content");
        assertThat(comment.assignedBy().id()).isEqualTo(183L);
        assertThat(comment.replyCount()).isEqualTo(1);

        assertThat(read("task-members", MembersResponse.class).members())
                .extracting("id")
                .containsExactly(812L, 813L);
        assertThat(read("space-tags", TagsResponse.class).tags().getFirst().name())
                .isEqualTo("Tag name");
    }

    @Test
    void readsTimeEntries() {
        TimeEntry entry = read("time-entries", TimeEntriesResponse.class).data().getFirst();

        assertThat(entry.start()).isEqualTo(1592841559129L);
        assertThat(entry.duration()).isEqualTo(4339892L);
        assertThat(entry.task().customId()).isEqualTo("JOSH-917");
        assertThat(entry.taskLocation().listId()).isEqualTo("1560300071");
        assertThat(entry.taskTags()).extracting("name").containsExactly("content-request", "marketing-okr");

        TimeEntry running = read("running-time-entry", TimeEntryResponse.class).data();
        assertThat(running.end()).isNull();
        assertThat(running.duration()).isNegative();
    }

    @Test
    void readsBulkTimeInStatusKeyedByTaskId() {
        Map<String, TaskTimeInStatus> byTask =
                ClickUpFixtures.read(mapper, "api/bulk-time-in-status.json", new TypeReference<>() {});

        var history = byTask.get("27075wz").statusHistory();
        assertThat(history).extracting("status").containsExactly("open", "active status #2");
        assertThat(history.get(1).totalTime().byMinute()).isEqualTo(23274L);
        assertThat(history.get(1).totalTime().since()).isEqualTo(1602607941692L);
    }

    @Test
    void readsGoals() {
        var goals = read("goals", GoalsResponse.class);
        assertThat(goals.goals().getFirst().members().getFirst().isCreator()).isTrue();
        assertThat(goals.folders().getFirst().goals().getFirst().percentCompleted())
                .isEqualTo(1);

        var goal = read("goal", GoalResponse.class).goal();
        assertThat(goal.owners().getFirst().id()).isEqualTo(182L);
        assertThat(goal.prettyUrl()).isEqualTo("https://app.clickup.com/512/goals/6");
    }

    @Test
    void readsWebhookRegistrations() {
        var webhook = read("webhooks", WebhooksResponse.class).webhooks().getFirst();
        assertThat(webhook.teamId()).isEqualTo(108L);
        assertThat(webhook.events()).hasSize(28).doesNotContain(WebhookEventType.UNKNOWN);
        assertThat(webhook.health().status()).isEqualTo("failing");
        assertThat(webhook.health().failCount()).isEqualTo(5);

        var created = read("create-webhook", WebhookResponse.class);
        assertThat(created.webhook().secret()).isNotBlank();
        assertThat(created.id()).isEqualTo(created.webhook().id());
    }

    @Test
    void readsErrorUnderEitherName() {
        assertThat(mapper.readValue("{\"err\":\"Token invalid\",\"ECODE\":\"OAUTH_025\"}", ClickUpError.class))
                .isEqualTo(new ClickUpError("Token invalid", "OAUTH_025"));
        assertThat(mapper.readValue("{\"error\":\"Name is required\",\"ECODE\":\"OAUTH_117\"}", ClickUpError.class)
                        .err())
                .isEqualTo("Name is required");
    }
}
