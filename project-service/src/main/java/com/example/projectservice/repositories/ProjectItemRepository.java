package com.example.projectservice.repositories;

import com.example.projectservice.domain.entities.ProjectItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectItemRepository extends JpaRepository<ProjectItem, Long> {
    List<ProjectItem> findAllByProjectId(Long projectId);
    List<ProjectItem> findAllByProjectIdAndPersonId(Long projectId, Long personId);
}
