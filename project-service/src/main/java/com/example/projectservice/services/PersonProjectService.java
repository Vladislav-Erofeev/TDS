package com.example.projectservice.services;


import com.example.projectservice.domain.entities.PersonProject;
import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.domain.entities.Project;
import com.example.projectservice.exceptions.LinkAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface PersonProjectService {
    boolean hasAuthority(Long personId, Long projectId, PersonProjectRole... roles);
    PersonProject save(Long personId, Project project, PersonProjectRole role);
    PersonProject getByPersonIdAndProjectId(Long personId, Long projectId);
    List<PersonProject> getAllByPersonId(Long personId);
    Integer getPersonsCountByProjectId(Long projectId);
    List<PersonProject> getAllPersonsByProjectId(Long projectId);
    void deletePersonProject(Long personId, Long projectId, Long adminId) throws IllegalAccessException;
    PersonProject editPersonProject(Long personId, Long projectId, PersonProjectRole role, Long principalId) throws IllegalAccessException;
}
