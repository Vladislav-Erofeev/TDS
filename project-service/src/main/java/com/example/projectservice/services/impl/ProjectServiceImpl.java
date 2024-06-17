package com.example.projectservice.services.impl;

import com.example.projectservice.domain.dtos.MessageActionType;
import com.example.projectservice.domain.dtos.MessageEvent;
import com.example.projectservice.domain.entities.*;
import com.example.projectservice.exceptions.LinkAlreadyExistException;
import com.example.projectservice.mappers.MessageMapper;
import com.example.projectservice.mappers.PropsMapper;
import com.example.projectservice.repositories.ProjectRepository;
import com.example.projectservice.services.MessageService;
import com.example.projectservice.services.PersonProjectService;
import com.example.projectservice.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final PersonProjectService personProjectService;
    private final MessageService messageService;
    private final MessageSendingOperations<String> messageTemplate;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Override
    public Project getById(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.orElseThrow(() -> new ObjectNotFoundException("Project", id));
    }

    @Override
    public Project save(Project project, Long personId) {
        project.setCreatedAt(new Date());
        project.setModifiedAt(new Date());
        Project saved = projectRepository.save(project);
        personProjectService.save(personId, saved, PersonProjectRole.OWNER);
        return saved;
    }

    @Override
    public Project attachProjectToPerson(Long personId, Long projectId) throws LinkAlreadyExistException {
        try {
            if (personProjectService.getByPersonIdAndProjectId(personId, projectId) != null)
                throw new LinkAlreadyExistException();
        } catch (ObjectNotFoundException ignore) {
        }
        Project project = getById(projectId);
        personProjectService.save(personId, project, PersonProjectRole.USER);

        // создание уведомления в чате о новом пользователе
        Message message = new Message();
        message.setProjectId(projectId);
        message.setPersonId(personId);
        message.setContent("ADD_USER");
        message = messageService.save(message, MessageType.CHAT_MESSAGE);
        // отправка всем подключённым клиентам
        messageTemplate.convertAndSend(
                "/messages/" + PropsMapper.encodeId(projectId),
                new MessageEvent(MessageActionType.SEND, messageMapper.toDto(message))
        );
        return project;
    }

    @Override
    public List<Project> getAllByPersonId(Long personId) {
        return personProjectService.getAllByPersonId(personId).stream().map(PersonProject::getProject).toList();
    }

    @Override
    public void deleteById(Long projectId, Long personId) throws IllegalAccessException {
        if (!personProjectService.hasAuthority(personId, projectId, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        projectRepository.deleteById(projectId);
    }

    @Override
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
