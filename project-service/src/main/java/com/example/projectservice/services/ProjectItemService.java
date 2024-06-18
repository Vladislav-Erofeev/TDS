package com.example.projectservice.services;

import com.example.projectservice.domain.entities.ProjectItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProjectItemService {
    @Transactional
    ProjectItem save(ProjectItem item, Long projectId) throws IllegalAccessException;

    List<ProjectItem> getAllByProjectId(Long projectId, Long personId) throws IllegalAccessException;

    List<ProjectItem> getAllByProjectIdAndPersonId(Long projectId, Long personId) throws IllegalAccessException;

    @Transactional
    void deleteById(Long itemId, Long personId) throws IllegalAccessException;
    ProjectItem getById(Long itemId, Long personId) throws IllegalAccessException;
}
