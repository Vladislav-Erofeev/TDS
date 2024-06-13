package com.example.projectservice.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "person_project")
@Getter
@Setter
@IdClass(PersonProject.PersonProjectId.class)
public class PersonProject {
    @Id
    private Long personId;
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;
    @Enumerated(value = EnumType.STRING)
    private PersonProjectRole role;

    @Getter
    @Setter
    public static class PersonProjectId implements Serializable {
        private Long personId;
        private Project project;
    }
}
