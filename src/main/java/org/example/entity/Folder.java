package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="folders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Folder {
    @Id
    @Column(name="id",nullable=false)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="space_id")
    private Space space;

    private Boolean hidden;
    private Boolean archived;

}
