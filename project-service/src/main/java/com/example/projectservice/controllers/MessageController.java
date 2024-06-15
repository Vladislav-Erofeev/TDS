package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.MessageDto;
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
    public List<MessageDto> getAllMessages(@PathVariable("projectId") String projectId) {
        return messageService.getMessagesByProjectId(PropsMapper.decodeId(projectId))
                .stream().map(messageMapper::toDto).toList();
    }
}
