package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user_tokens",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_tokens_user_workspace",
                columnNames = {"user_id", "workspace_id"}))
public class UserTokens{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(name="access_token")
    private String accessToken;

    @Column(name="updated_at")
    private Timestamp updatedAt;

}
