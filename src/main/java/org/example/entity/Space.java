package org.example.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.dto.StatusDto;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "spaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Space {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    private String name;

    @Column(name = "is_private")
    private Boolean isPrivate;

    private Boolean archived;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "statuses", columnDefinition = "jsonb")
    private List<StatusDto> statuses = new ArrayList<>();
}
