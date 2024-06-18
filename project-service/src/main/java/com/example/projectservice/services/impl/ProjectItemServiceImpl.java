package com.example.projectservice.services.impl;

import com.example.projectservice.domain.entities.ProjectItem;
import com.example.projectservice.repositories.ProjectItemRepository;
import com.example.projectservice.services.PersonProjectService;
import com.example.projectservice.services.ProjectItemService;
import com.example.projectservice.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectItemServiceImpl implements ProjectItemService {
    private final ProjectService projectService;
    private final PersonProjectService personProjectService;
    private final ProjectItemRepository projectItemRepository;

    @Override
    public ProjectItem save(ProjectItem item, Long projectId) throws IllegalAccessException {
        if (!personProjectService.hasAnyAuthority(item.getPersonId(), projectId))
            throw new IllegalAccessException();

        item.setCreationDate(new Date());
        item.setModifiedDate(new Date());
        item.setProject(projectService.getById(projectId));
        return projectItemRepository.save(item);
    }

    @Override
    public List<ProjectItem> getAllByProjectId(Long projectId, Long personId) throws IllegalAccessException {
        if (!personProjectService.hasAnyAuthority(personId, projectId))
            throw new IllegalAccessException();
        return projectItemRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<ProjectItem> getAllByProjectIdAndPersonId(Long projectId, Long personId) throws IllegalAccessException {
        if (!personProjectService.hasAnyAuthority(personId, projectId))
            throw new IllegalAccessException();
        return projectItemRepository.findAllByProjectIdAndPersonId(projectId, personId);
    }

    @Override
    public ProjectItem getById(Long itemId, Long personId) throws IllegalAccessException {
        ProjectItem item = projectItemRepository.findById(itemId).orElseThrow(() -> new ObjectNotFoundException("Item", itemId));
        if (!personProjectService.hasAnyAuthority(personId, item.getProject().getId()))
            throw new IllegalAccessException();
        return item;
    }

    @Override
    public void deleteById(Long itemId, Long personId) throws IllegalAccessException {
        ProjectItem item = getById(itemId, personId);
        projectItemRepository.delete(item);
    }
}
