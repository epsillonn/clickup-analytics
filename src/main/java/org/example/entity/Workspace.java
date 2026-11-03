package org.example.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="workspaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {

    @Id
    private String id;

    private String name;



}
