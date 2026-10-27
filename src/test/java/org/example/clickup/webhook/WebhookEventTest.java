package org.example.clickup.webhook;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import org.example.clickup.ClickUpFixtures;
import org.example.clickup.model.Priority;
import org.example.clickup.model.Status;
import org.example.clickup.model.Tag;
import org.example.clickup.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@JsonTest
class WebhookEventTest {
    @Autowired
    private JsonMapper mapper;

    private WebhookEvent read(String event) {
        return ClickUpFixtures.read(mapper, "webhook/" + event + ".json", WebhookEvent.class);
    }

    private HistoryItem onlyHistoryItem(String event) {
        return read(event).historyItems().getFirst();
    }

    @Test
    void readsEnvelopeOfTaskCreated() {
        WebhookEvent event = read("taskCreated");

        assertThat(event.event()).isEqualTo(WebhookEventType.TASK_CREATED);
        assertThat(event.taskId()).isEqualTo("1vj37mc");
        assertThat(event.webhookId()).isEqualTo("7fa3ec74-69a8-4530-a251-8a13730bd204");
        assertThat(event.historyItems()).extracting(HistoryItem::field).containsExactly("status", "task_creation");

        HistoryItem item = event.historyItems().getFirst();
        assertThat(item.date()).isEqualTo(1642734631523L);
        assertThat(item.parentId()).isEqualTo("162641062");
        assertThat(item.user().id()).isEqualTo(183L);
    }

    @Test
    void readsEventsWithoutHistoryItems() {
        assertThat(read("listCreated").listId()).isEqualTo("162641234");
        assertThat(read("spaceUpdated").spaceId()).isEqualTo("7002367");
        assertThat(read("taskDeleted").historyItems()).isNull();

        WebhookEvent keyResult = read("keyResultUpdated");
        assertThat(keyResult.event()).isEqualTo(WebhookEventType.KEY_RESULT_UPDATED);
        assertThat(keyResult.goalId()).isEqualTo("a23e5a3d-74b5-44c2-ab53-917ebe85045a");
        assertThat(keyResult.keyResultId()).isEqualTo("47608e42-ad0e-4934-a39e-950539c77e79");
    }

    @Test
    void convertsBeforeAndAfterByField() {
        HistoryItem status = onlyHistoryItem("taskStatusUpdated");
        assertThat(mapper.treeToValue(status.before(), Status.class).type()).isEqualTo("removed");
        assertThat(mapper.treeToValue(status.after(), Status.class).status()).isEqualTo("to do");

        HistoryItem priority = onlyHistoryItem("taskPriorityUpdated");
        assertThat(priority.before().isNull()).isTrue();
        assertThat(mapper.treeToValue(priority.after(), Priority.class).priority())
                .isEqualTo("high");

        HistoryItem assignee = onlyHistoryItem("taskAssigneeUpdated");
        assertThat(assignee.field()).isEqualTo("assignee_add");
        assertThat(mapper.treeToValue(assignee.after(), User.class).username()).isEqualTo("Sam");

        HistoryItem dueDate = onlyHistoryItem("taskDueDateUpdated");
        assertThat(dueDate.after().asLong()).isEqualTo(1643608800000L);

        HistoryItem tags = onlyHistoryItem("taskTagUpdated");
        List<Tag> added = mapper.convertValue(tags.after(), new TypeReference<>() {});
        assertThat(added).extracting(Tag::name).containsExactly("def");

        HistoryItem moved = onlyHistoryItem("taskMoved");
        MovedLocation from = mapper.treeToValue(moved.before(), MovedLocation.class);
        MovedLocation to = mapper.treeToValue(moved.after(), MovedLocation.class);
        assertThat(from.id()).isEqualTo("162641062");
        assertThat(to.id()).isEqualTo("162641285");
        assertThat(to.project().name()).isEqualTo("This is my API Space");
    }

    @Test
    void readsTimeTracking() {
        WebhookEvent event = read("taskTimeTrackedUpdated");
        assertThat(event.data().intervalId()).isEqualTo("2800809188061119507");

        TrackedInterval interval =
                mapper.treeToValue(event.historyItems().getFirst().after(), TrackedInterval.class);
        assertThat(interval.time()).isEqualTo(900000L);
        assertThat(interval.end() - interval.start()).isEqualTo(interval.time());

        HistoryItem estimate = onlyHistoryItem("taskTimeEstimateUpdated");
        assertThat(estimate.data().get("time_estimate").asLong()).isEqualTo(5400000L);
    }

    @Test
    void readsCommentAndCustomField() {
        WebhookComment comment = onlyHistoryItem("taskCommentPosted").comment();
        assertThat(comment.textContent()).isEqualTo("comment abc1234\n");
        assertThat(comment.parent()).isEqualTo("1vj38vv");
        assertThat(comment.threadFollowers()).extracting(User::id).containsExactly(183L);

        HistoryItem customField = onlyHistoryItem("taskUpdated-custom-field");
        assertThat(customField.field()).isEqualTo("custom_field");
        assertThat(customField.after().asString()).isEqualTo("5048f827-f16a-47b0-afec-5fd0e51b5f50");
        assertThat(customField.customField().type()).isEqualTo("drop_down");
        assertThat(customField.customField().typeConfig().get("options")).hasSize(4);
    }

    @Test
    void mapsUnrecognizedEventToUnknown() {
        WebhookEvent event = mapper.readValue(
                "{\"event\":\"docCreated\",\"webhook_id\":\"w\",\"doc_id\":\"d\"}", WebhookEvent.class);

        assertThat(event.event()).isEqualTo(WebhookEventType.UNKNOWN);
    }

    @Test
    void readsAutomationPayloads() {
        var current = ClickUpFixtures.read(mapper, "webhook/automation.json", AutomationWebhookPayload.class);
        assertThat(current.autoId()).isEqualTo("c389706e-3a5f-4230-a2aa-cdea3dc1e4cc:main");
        assertThat(current.date()).isEqualTo(Instant.parse("2025-04-16T23:49:06.457Z"));
        assertThat(current.payload().get("id").asString()).isEqualTo("868djdyr0");

        var legacy = ClickUpFixtures.read(mapper, "webhook/automation-legacy.json", AutomationWebhookPayload.class);
        assertThat(legacy.autoId()).isNull();
        assertThat(legacy.id()).isEqualTo("b4ced072-ae72-4c70-b898-fea5dd142408:main");
        assertThat(legacy.payload().get("status").get("type").asString()).isEqualTo("closed");
    }
}
