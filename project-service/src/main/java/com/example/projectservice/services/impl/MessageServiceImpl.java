package com.example.projectservice.services.impl;

import com.example.projectservice.domain.entities.Message;
import com.example.projectservice.repositories.MessageRepository;
import com.example.projectservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message save(Message message) {
        message.setSendTime(new Date());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) {
        return messageRepository.getAllByProjectId(projectId);
    }
}
