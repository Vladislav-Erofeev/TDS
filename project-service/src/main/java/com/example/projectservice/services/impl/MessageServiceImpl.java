package com.example.projectservice.services.impl;

import com.example.projectservice.domain.entities.Message;
import com.example.projectservice.repositories.MessageRepository;
import com.example.projectservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
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
    @Transactional
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Message editById(Message message) {
        Message oldMessage = getById(message.getId());
        oldMessage.setContent(message.getContent());
        oldMessage.setEdited(true);
        return messageRepository.save(oldMessage);
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) {
        return messageRepository.getAllByProjectIdOrderById(projectId);
    }

    public Message getById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Message", id));
    }
}
