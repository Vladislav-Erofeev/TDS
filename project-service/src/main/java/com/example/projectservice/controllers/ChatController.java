package com.example.projectservice.controllers;

import com.example.projectservice.domain.dtos.MessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/{projectId}")
    @SendTo("/messages/{projectId}")
    public MessageDto handleMessage(@Payload MessageDto message) {
        return message;
    }
}
