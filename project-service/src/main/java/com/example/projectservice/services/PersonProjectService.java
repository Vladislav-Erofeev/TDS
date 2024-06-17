package com.example.projectservice.services;


import com.example.projectservice.domain.entities.PersonProject;
import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.domain.entities.Project;
import org.hibernate.ObjectNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface PersonProjectService {
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    boolean hasAuthority(Long personId, Long projectId, PersonProjectRole... roles);

    PersonProject save(Long personId, Project project, PersonProjectRole role);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    PersonProject getByPersonIdAndProjectId(Long personId, Long projectId) throws ObjectNotFoundException;

    List<PersonProject> getAllByPersonId(Long personId);

    Integer getPersonsCountByProjectId(Long projectId);

    List<PersonProject> getAllPersonsByProjectId(Long projectId);

    @Transactional
    void deletePersonProject(Long personId, Long projectId, Long adminId) throws IllegalAccessException;

    @Transactional
    PersonProject editPersonProject(Long personId, Long projectId, PersonProjectRole role, Long principalId) throws IllegalAccessException;
}
