package com.example.projectservice.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Project {
    @Column(name = "project_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String comment;
    private Date createdAt;
    private Date modifiedAt;
    @OneToMany(mappedBy = "project")
    private List<PersonProject> personProjects = new LinkedList<>();
    @OneToMany(mappedBy = "project")
    private List<ProjectItem> items = new LinkedList<>();

    public void addPersonProject(PersonProject personProject) {
        personProjects.add(personProject);
    }
}
