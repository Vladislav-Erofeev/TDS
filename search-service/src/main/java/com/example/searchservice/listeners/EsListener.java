package com.example.searchservice.listeners;

import com.example.searchservice.messages.ItemMessage;
import com.example.searchservice.services.EsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EsListener {
    private final EsService esService;

    @KafkaListener(topics = "db.public.item", concurrency = "5",
            containerFactory = "tableMessageConcurrentKafkaListenerContainerFactory")
    public void handleTest(@Payload(required = false) ItemMessage message) throws IOException {
        if (message == null)
            return;
        switch (message.getOp()) {
            case c -> esService.save(message.getAfter().getId(), message.getAfter());
            case d -> esService.deleteById(message.getBefore().getId());
            case u -> esService.updateById(message.getAfter().getId(), message.getAfter());
            case r -> esService.validateItem(message.getAfter().getId(), message.getAfter());
        }
    }
}
