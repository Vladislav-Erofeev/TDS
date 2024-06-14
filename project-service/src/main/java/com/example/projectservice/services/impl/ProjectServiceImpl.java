package com.example.projectservice.services.impl;

import com.example.projectservice.domain.entities.PersonProject;
import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.domain.entities.Project;
import com.example.projectservice.exceptions.LinkAlreadyExistException;
import com.example.projectservice.repositories.ProjectRepository;
import com.example.projectservice.services.PersonProjectService;
import com.example.projectservice.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final PersonProjectService personProjectService;

    public Project getById(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.orElseThrow(() -> new ObjectNotFoundException("Project", id));
    }

    @Transactional
    public Project save(Project project, Long personId) {
        project.setCreatedAt(new Date());
        project.setModifiedAt(new Date());
        Project saved = projectRepository.save(project);
        personProjectService.save(personId, saved, PersonProjectRole.OWNER);
        return saved;
    }

    @Transactional
    public void attachProjectToPerson(Long personId, Long projectId) throws LinkAlreadyExistException {
        if (personProjectService.getByPersonIdAndProjectId(personId, projectId).isPresent())
            throw new LinkAlreadyExistException();
        Project project = getById(projectId);
        personProjectService.save(personId, project, PersonProjectRole.USER);
    }

    public List<Project> getAllByPersonId(Long personId) {
        return personProjectService.getAllByPersonId(personId).stream().map(PersonProject::getProject).toList();
    }

    @Transactional
    public void deleteById(Long projectId, Long personId) throws IllegalAccessException {
        if (!personProjectService.hasAuthority(personId, projectId, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public Project editById(Long projectId, Long personId, Project project) throws IllegalAccessException {
        if (!personProjectService.hasAuthority(personId, projectId, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        Project oldProject = getById(projectId);
        oldProject.setName(project.getName());
        oldProject.setComment(project.getComment());
        oldProject.setModifiedAt(new Date());
        return projectRepository.save(oldProject);
    }
}
