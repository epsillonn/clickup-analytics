package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private Long id;

    private String username;
    private String email;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Builder.Default
    @Column(name = "role")
    private String role = "DEVELOPER";
}
