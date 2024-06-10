package com.example.searchservice.services;

import com.example.searchservice.entities.GeocodedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Map<Long, FluxSink<ServerSentEvent>> subscribers = new HashMap<>();

    public void subscribe(Long id, FluxSink<ServerSentEvent> fluxSink) {
        subscribers.put(id, fluxSink);
    }

    public void unsubscribe(Long personId) {
        subscribers.remove(personId);
    }

    public void sendMessage(Long personId, GeocodedFile geocodedFile) {
        if (!subscribers.containsKey(personId))
            return;

        ServerSentEvent<GeocodedFile> serverSentEvent = ServerSentEvent.builder(geocodedFile).build();
        subscribers.get(personId).next(serverSentEvent);
    }
}
