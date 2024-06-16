package com.example.projectservice.services.impl;

import com.example.projectservice.domain.entities.PersonProject;
import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.domain.entities.Project;
import com.example.projectservice.repositories.PersonProjectRepository;
import com.example.projectservice.services.PersonProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonProjectServiceImpl implements PersonProjectService {
    private final PersonProjectRepository personProjectRepository;

    @Override
    public Integer getPersonsCountByProjectId(Long projectId) {
        return personProjectRepository.countAllByProjectId(projectId);
    }

    @Override
    @Transactional
    public void deletePersonProject(Long personId, Long projectId, Long adminId) throws IllegalAccessException {
        if (!personId.equals(adminId) && !hasAuthority(adminId, projectId, PersonProjectRole.ADMIN, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        personProjectRepository.deleteByPersonIdAndAndProjectId(personId, projectId);
    }

    @Override
    @Transactional
    public PersonProject editPersonProject(Long personId, Long projectId, PersonProjectRole role, Long principalId) throws IllegalAccessException {
        if (!hasAuthority(principalId, projectId, PersonProjectRole.ADMIN, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        PersonProject personProject = getByPersonIdAndProjectId(personId, projectId);
        personProject.setRole(role);
        return personProjectRepository.save(personProject);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean hasAuthority(Long personId, Long projectId, PersonProjectRole... roles) {
        PersonProject personProject = getByPersonIdAndProjectId(personId, projectId);
        for (PersonProjectRole role : roles)
            if (personProject.getRole().equals(role))
                return true;
        return false;
    }

    @Transactional
    @Override
    public PersonProject save(Long personId, Project project, PersonProjectRole role) {
        PersonProject personProject = new PersonProject();
        personProject.setPersonId(personId);
        personProject.setProject(project);
        personProject.setRole(role);
        return personProjectRepository.save(personProject);
    }

    @Override
    public PersonProject getByPersonIdAndProjectId(Long personId, Long projectId) {
        return personProjectRepository.findAllByPersonIdAndProjectId(personId, projectId)
                .orElseThrow(() -> new ObjectNotFoundException("PersonProject", personId));
    }

    @Override
    public List<PersonProject> getAllByPersonId(Long personId) {
        return personProjectRepository.findAllByPersonId(personId);
    }

    @Override
    public List<PersonProject> getAllPersonsByProjectId(Long projectId) {
        return personProjectRepository.findAllByProjectId(projectId);
    }
}
