package org.example.entity;
import jakarta.persistence.*;
import lombok.*;
import org.example.enums.ClickupRole;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="workspace_member")
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //şart mı

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "invited_by_user_id")
    private User invitedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable=true)
    private ClickupRole role;

    @Column(name="date_invited")
    private  String dateInvited;

    @Column(name = "date_joined")
    private String dateJoined;


}
