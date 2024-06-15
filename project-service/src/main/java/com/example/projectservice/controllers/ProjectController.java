package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.ProjectDto;
import com.example.projectservice.domain.entities.Project;
import com.example.projectservice.exceptions.InvalidInviteLinkException;
import com.example.projectservice.exceptions.LinkAlreadyExistException;
import com.example.projectservice.mappers.ProjectMapper;
import com.example.projectservice.mappers.PropsMapper;
import com.example.projectservice.services.PersonProjectService;
import com.example.projectservice.services.ProjectService;
import com.example.projectservice.utlis.InviteGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
@PreAuthorize("isAuthenticated()")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper = ProjectMapper.INSTANCE;
    private final InviteGenerator inviteGenerator;
    private final PersonProjectService personProjectService;

    @PostMapping
    public ProjectDto save(@RequestBody ProjectDto projectDto,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return projectMapper.toDto(projectService.save(projectMapper.toEntity(projectDto),
                principal.getAttribute("id")));
    }

    @GetMapping
    public List<ProjectDto> getAllByPersonId(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return projectService.getAllByPersonId(principal.getAttribute("id"))
                .stream().map(projectMapper::toDto).toList();
    }

    @GetMapping("/{projectId}")
    public ProjectDto getById(@PathVariable("projectId") String id) {
        ProjectDto projectDto = projectMapper.toDto(projectService.getById(PropsMapper.decodeId(id)));
        projectDto.setPersonsCount(personProjectService.getPersonsCountByProjectId(PropsMapper.decodeId(id)));
        return projectDto;
    }

    @PatchMapping("/{projectId}")
    public ProjectDto editById(@PathVariable("projectId") String id,
                               @RequestBody ProjectDto projectDto,
                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) throws IllegalAccessException {
        return projectMapper.toDto(projectService.editById(PropsMapper.decodeId(id),
                principal.getAttribute("id"),
                projectMapper.toEntity(projectDto)));
    }

    @DeleteMapping("/{projectId}")
    public void deleteById(@PathVariable("projectId") String id,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) throws IllegalAccessException {
        projectService.deleteById(PropsMapper.decodeId(id), principal.getAttribute("id"));
    }

    @GetMapping("/invite")
    public ProjectDto attachPersonToProject(@RequestParam("token") String hash,
                                      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) throws InvalidInviteLinkException, LinkAlreadyExistException {
        Project project = projectService.attachProjectToPerson(principal.getAttribute("id"),
                inviteGenerator.validateHash(hash));
        ProjectDto projectDto = projectMapper.toDto(project);
        projectDto.setPersonsCount(personProjectService.getPersonsCountByProjectId(project.getId()));
        return projectDto;
    }

    @GetMapping("/{projectId}/invite_token")
    public String generateInviteHash(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                     @PathVariable("projectId") String projectId) throws IllegalAccessException {
        return inviteGenerator.generateHash(principal.getAttribute("id"), PropsMapper.decodeId(projectId));
    }
}
