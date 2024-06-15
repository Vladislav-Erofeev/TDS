package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.MessageDto;
import com.example.projectservice.mappers.MessageMapper;
import com.example.projectservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @MessageMapping("/chat/{projectId}")
    @SendTo("/messages/{projectId}")
    public MessageDto handleMessage(@Payload MessageDto message) {
        return messageMapper.toDto(messageService.save(messageMapper.toEntity(message)));
    }
}
