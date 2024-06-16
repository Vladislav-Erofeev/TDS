package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.PersonProjectDto;
import com.example.projectservice.domain.entities.PersonProjectRole;
import com.example.projectservice.mappers.PersonProjectMapper;
import com.example.projectservice.mappers.PropsMapper;
import com.example.projectservice.services.PersonProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PersonProjectController {
    private final PersonProjectService personProjectService;
    private final PersonProjectMapper personProjectMapper = PersonProjectMapper.INSTANCE;

    @GetMapping("/{projectId}")
    public List<PersonProjectDto> getAllPersonsByProjectId(@PathVariable("projectId") String projectId) {
        return personProjectService.getAllPersonsByProjectId(PropsMapper.decodeId(projectId))
                .stream().map(personProjectMapper::toDto).toList();
    }

    @DeleteMapping("/{personId}")
    public void deletePersonFromProject(@PathVariable("personId") String personId,
                                        @RequestParam("projectId") String projectId,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) throws IllegalAccessException {
        personProjectService.deletePersonProject(PropsMapper.decodeId(personId), PropsMapper.decodeId(projectId),
                principal.getAttribute("id"));
    }

    @PatchMapping("/{personId}")
    public void setPersonProjectRole(@PathVariable("personId") String personId,
                                     @RequestParam("projectId") String projectId,
                                     @RequestParam("role") PersonProjectRole role,
                                     @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal)
            throws IllegalAccessException {
        personProjectService.editPersonProject(PropsMapper.decodeId(personId), PropsMapper.decodeId(projectId),
                role, principal.getAttribute("id"));
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    public ResponseEntity<String> illegalAccessException(IllegalAccessException e) {
        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }
}
