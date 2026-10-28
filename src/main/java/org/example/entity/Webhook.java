package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@Table(name="webhooks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Webhook {
    @Id
    @Column(name="id")
    private String id;

    @OneToOne
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by_user")
    private User user;

    @Column(name="secret")
    private String secret;

    @Column(name="status")
    private String status;

    @Column(name="fail_count")
    private Integer failCount=0;

    @Column(name="created_at")
    private Timestamp createdAt;


}
