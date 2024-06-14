package com.example.projectservice.services;


import com.example.projectservice.domain.entities.PersonProjectRole;

public interface PersonProjectService {
    boolean hasAuthority(Long personId, Long projectId, PersonProjectRole... roles);
}
