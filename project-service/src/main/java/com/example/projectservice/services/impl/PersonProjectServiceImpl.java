package com.example.projectservice.services.impl;

import com.example.projectservice.domain.entities.PersonProject;
import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.repositories.PersonProjectRepository;
import com.example.projectservice.services.PersonProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
public class PersonProjectServiceImpl implements PersonProjectService {
    private final PersonProjectRepository personProjectRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean hasAuthority(Long personId, Long projectId, PersonProjectRole... roles) {
        PersonProject personProject = personProjectRepository.findAllByPersonIdAndProjectId(personId, projectId)
                .orElseThrow(() -> new ObjectNotFoundException("PersonProject", personId));
        for (PersonProjectRole role : roles)
            if (personProject.getRole().equals(role))
                return true;
        return false;
    }
}
