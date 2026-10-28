package org.example.entity;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @Column(name="id")
    private String id;

    @ManyToOne()
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    @ManyToOne()
    @JoinColumn(name="list_id")
    private Lists lists;

    private String name;
    @Column(name="status_name")
    private String statusName;

    @Column(name="status_type")
    private String statusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent")
    private Task parent;//taskid fk olucak


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="top_level_parent")
    private Task topLevelParent;


    @Column(name="time_estimate")
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
    private User creator; //belki assignee tablosunda olabilir

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_by_task_id")
    private Task blockedByTaskId; // bu da aslında fk task ıd olacak


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id")
    private Lists list; //? list ıd yani string
}
