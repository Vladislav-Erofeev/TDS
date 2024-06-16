package com.example.projectservice.repositories;

import com.example.projectservice.domain.entities.PersonProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonProjectRepository extends JpaRepository<PersonProject, PersonProject.PersonProjectId> {
    List<PersonProject> findAllByPersonId(Long personId);
    Optional<PersonProject> findAllByPersonIdAndProjectId(Long personId, Long projectId);
    Integer countAllByProjectId(Long projectId);
    List<PersonProject> findAllByProjectId(Long projectId);
    void deleteByPersonIdAndAndProjectId(Long personId, Long projectId);
}
