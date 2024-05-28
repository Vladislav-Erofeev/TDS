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
public class TestListener {
    private final EsService esService;

    @KafkaListener(topics = "db.public.item", concurrency = "5",
            containerFactory = "tableMessageConcurrentKafkaListenerContainerFactory")
    public void handleTest(@Payload(required = false) ItemMessage message) throws IOException {
        if (message == null)
            return;
        switch (message.getOp()) {
            case c -> esService.save(String.valueOf(message.getAfter().getId()), message.getAfter());
            case d -> esService.deleteById(String.valueOf(message.getBefore().getId()));
            case u -> esService.updateById(String.valueOf(message.getAfter().getId()), message.getAfter());
        }
    }
}
