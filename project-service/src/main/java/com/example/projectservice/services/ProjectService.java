package com.example.projectservice.services;

import com.example.projectservice.domain.entities.Project;
import com.example.projectservice.exceptions.LinkAlreadyExistException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProjectService {
    Project getById(Long id);

    @Transactional
    Project save(Project project, Long personId);

    @Transactional
    Project attachProjectToPerson(Long personId, Long projectId) throws LinkAlreadyExistException;

    List<Project> getAllByPersonId(Long personId);

    @Transactional
    void deleteById(Long projectId, Long personId) throws IllegalAccessException;

    @Transactional
    Project editById(Long projectId, Long personId, Project project) throws IllegalAccessException;
}
