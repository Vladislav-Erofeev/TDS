package com.example.projectservice.services;

import com.example.projectservice.domain.entities.Project;
import com.example.projectservice.exceptions.LinkAlreadyExistException;

import java.util.List;

public interface ProjectService {
    Project getById(Long id);

    Project save(Project project, Long personId);

    void attachProjectToPerson(Long personId, Long projectId) throws LinkAlreadyExistException;

    List<Project> getAllByPersonId(Long personId);

    void deleteById(Long projectId, Long personId) throws IllegalAccessException;

    Project editById(Long projectId, Long personId, Project project) throws IllegalAccessException;
}
