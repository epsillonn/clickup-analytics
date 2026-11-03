package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dto.FolderResponseDto;
import org.example.dto.StatusDto;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name="lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lists {

    @Id
    @Column(name="id")
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name="task_count")
    private Integer taskCount;

    @Column(name = "parent_task_count")
    private Integer parentTaskCount;

    @Column(name = "total_task_count")
    private Integer totalTaskCount;

    @Column(name = "subtask_count")
    private Integer subtaskCount;

    private Boolean archived;

    @Column(name = "override_statuses")
    private Boolean overrideStatuses;

   @JdbcTypeCode(SqlTypes.JSON)
   @Column(name = "statuses", columnDefinition = "jsonb")
   @Builder.Default
   private List<StatusDto> statuses = new ArrayList<>();





}
