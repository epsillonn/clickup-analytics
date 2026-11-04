package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @Column(name = "id")
    private String id;

    private String name;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_type")
    private String statusType;

    @ManyToOne()
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "list_id")
    private Lists lists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Task parent; // taskid fk olucak

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_level_parent")
    private Task topLevelParent;

    @Column(name = "time_estimate")
    private String timeEstimate;

    private String priority;

    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "date_updated")
    private String dateUpdated;

    @Column(name = "date_closed")
    private String dateClosed;

    @Column(name = "date_done")
    private String dateDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator; // belki assignee tablosunda olabilir

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_by_task_id")
    private Task blockedByTaskId; // bu da aslında fk task ıd olacak

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Lists list; // ? list ıd yani string
}
