package com.example.projectservice.services;

import com.example.projectservice.domain.entities.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MessageService {
    @Transactional
    Message save(Message message);
    List<Message> getMessagesByProjectId(Long projectId);
    @Transactional
    void deleteById(Long id);
    @Transactional
    Message editById(Message message);
}
