package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.MessageEvent;
import com.example.projectservice.domain.dtos.MessageActionType;
import com.example.projectservice.mappers.MessageMapper;
import com.example.projectservice.mappers.PropsMapper;
import com.example.projectservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{projectId}")
    public List<MessageEvent> getAllMessages(@PathVariable("projectId") String projectId) {
        return messageService.getMessagesByProjectId(PropsMapper.decodeId(projectId))
                .stream().map(message -> new MessageEvent(MessageActionType.SEND, messageMapper.toDto(message))).toList();
    }
}
