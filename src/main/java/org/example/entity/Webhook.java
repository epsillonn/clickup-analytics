package org.example.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "webhooks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Webhook {
    @Id
    @Column(name = "id", nullable = false)
    private String id; // webhhok id pk olucak? yoksa ekstra ıd mı olmalı webhook id pk olmamalımı

    @OneToOne
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Column(name = "endpoint_url", nullable = false, length = 500)
    private String endpointUrl; // URL ? belkide dbde olmamalı just env enough

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user", nullable = false)
    private User user;

    @Column(name = "secret", nullable = false)
    private String secret;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "fail_count")
    private Integer failCount = 0;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
