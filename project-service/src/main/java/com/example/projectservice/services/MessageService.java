package com.example.projectservice.services;

import com.example.projectservice.domain.entities.Message;

import java.util.List;

public interface MessageService {
    Message save(Message message);
    List<Message> getMessagesByProjectId(Long projectId);
}
