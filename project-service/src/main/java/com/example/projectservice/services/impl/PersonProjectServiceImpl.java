package com.example.projectservice.services.impl;

import com.example.projectservice.domain.dtos.MessageActionType;
import com.example.projectservice.domain.dtos.MessageEvent;
import com.example.projectservice.domain.entities.*;
import com.example.projectservice.mappers.MessageMapper;
import com.example.projectservice.mappers.PropsMapper;
import com.example.projectservice.repositories.PersonProjectRepository;
import com.example.projectservice.services.MessageService;
import com.example.projectservice.services.PersonProjectService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonProjectServiceImpl implements PersonProjectService {
    private final PersonProjectRepository personProjectRepository;
    private final MessageService messageService;
    private final MessageSendingOperations<String> messageTemplate;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Override
    public Integer getPersonsCountByProjectId(Long projectId) {
        return personProjectRepository.countAllByProjectId(projectId);
    }

    @Override
    public void deletePersonProject(Long personId, Long projectId, Long adminId) throws IllegalAccessException {
        if (!personId.equals(adminId) && !hasAuthority(adminId, projectId, PersonProjectRole.ADMIN, PersonProjectRole.OWNER))
            throw new IllegalAccessException();

        // создание уведомления в чате о новом пользователе
        Message message = new Message();
        message.setProjectId(projectId);
        message.setPersonId(personId);
        message.setContent(personId.equals(adminId) ? "REMOVE_USER" : "KICK_USER");
        message = messageService.save(message, MessageType.CHAT_MESSAGE);
        // отправка всем подключённым клиентам
        messageTemplate.convertAndSend(
                "/messages/" + PropsMapper.encodeId(projectId),
                new MessageEvent(MessageActionType.SEND, messageMapper.toDto(message))
        );
        personProjectRepository.deleteByPersonIdAndAndProjectId(personId, projectId);
    }

    @Override
    public PersonProject editPersonProject(Long personId, Long projectId, PersonProjectRole role, Long principalId) throws IllegalAccessException {
        if (!hasAuthority(principalId, projectId, PersonProjectRole.ADMIN, PersonProjectRole.OWNER))
            throw new IllegalAccessException();
        PersonProject personProject = getByPersonIdAndProjectId(personId, projectId);
        personProject.setRole(role);
        return personProjectRepository.save(personProject);
    }

    @Override
    public boolean hasAuthority(Long personId, Long projectId, PersonProjectRole... roles) {
        PersonProject personProject = getByPersonIdAndProjectId(personId, projectId);
        for (PersonProjectRole role : roles)
            if (personProject.getRole().equals(role))
                return true;
        return false;
    }

    @Override
    public boolean hasAnyAuthority(Long personId, Long projectId) {
        try {
            getByPersonIdAndProjectId(personId, projectId);
            return true;
        } catch (ObjectNotFoundException e) {
            return false;
        }
    }

    @Override
    public PersonProject save(Long personId, Project project, PersonProjectRole role) {
        PersonProject personProject = new PersonProject();
        personProject.setPersonId(personId);
        personProject.setProject(project);
        personProject.setRole(role);
        return personProjectRepository.save(personProject);
    }

    @Override
    public PersonProject getByPersonIdAndProjectId(Long personId, Long projectId) throws ObjectNotFoundException {
        return personProjectRepository.findAllByPersonIdAndProjectId(personId, projectId)
                .orElseThrow(() -> new ObjectNotFoundException("PersonProject", personId));
    }

    @Override
    public List<PersonProject> getAllByPersonId(Long personId) {
        return personProjectRepository.findAllByPersonId(personId);
    }

    @Override
    public List<PersonProject> getAllPersonsByProjectId(Long projectId) {
        return personProjectRepository.findAllByProjectId(projectId);
    }
}
