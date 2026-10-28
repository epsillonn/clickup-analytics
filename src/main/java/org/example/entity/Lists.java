package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(name="task_count")
    private Integer taskCount;

   // @JdbcTypeCode(SqlTypes.JSON)
   // @Column(name = "statuses", columnDefinition = "jsonb")
   // private List<ClickUpStatusDto> statuses = new ArrayList<>();





}
