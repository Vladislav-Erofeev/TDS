package com.example.searchservice.listeners;

import com.example.searchservice.messages.TestTableMessage;
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

    @KafkaListener(topics = "postgres.public.test", concurrency = "5",
            containerFactory = "tableMessageConcurrentKafkaListenerContainerFactory")
    public void handleTest(@Payload(required = false) TestTableMessage message) throws IOException {
        if (message == null)
            return;
        switch (message.getOp()) {
            case "c" -> esService.save(message.getAfter().getId().toString(), message.getAfter().getTitle(),
                    message.getAfter().getBody());
            case "u" -> esService.updateById(message.getBefore().getId().toString(), message.getAfter().getTitle(),
                    message.getAfter().getBody());
            case "d" -> esService.deleteById(message.getBefore().getId().toString());
        }
    }
}
