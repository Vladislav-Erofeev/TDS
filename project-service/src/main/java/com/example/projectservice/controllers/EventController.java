package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.ActiveUserEvent;
import com.example.projectservice.domain.dtos.MessageActionType;
import com.example.projectservice.domain.dtos.MessageEvent;
import com.example.projectservice.domain.entities.MessageType;
import com.example.projectservice.mappers.MessageMapper;
import com.example.projectservice.mappers.PropsMapper;
import com.example.projectservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final MessageService messageService;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;
    @MessageMapping("/user_events/{projectId}")
    @SendTo("/broker/user_events/{projectId}")
    public ActiveUserEvent handleActiveUserEvent(@Payload ActiveUserEvent activeUserEvent) {
        return activeUserEvent;
    }

    @MessageMapping("/chat/{projectId}")
    @SendTo("/broker/chat/{projectId}")
    public MessageEvent handleMessage(@Payload MessageEvent messageEvent) {
        switch (messageEvent.getType()) {
            case SEND -> {
                return new MessageEvent(MessageActionType.SEND,
                        messageMapper.toDto(messageService.save(messageMapper.toEntity(messageEvent.getMessage()),
                                MessageType.USER_MESSAGE)));
            }
            case EDIT -> {
                return new MessageEvent(MessageActionType.EDIT,
                        messageMapper.toDto(messageService.editById(messageMapper.toEntity(messageEvent.getMessage()))));
            }
            case DELETE -> {
                messageService.deleteById(PropsMapper.decodeId(messageEvent.getMessage().getId()));
                return messageEvent;
            }
        }
        return null;
    }
}
