package com.example.searchservice.controllers;

import com.example.searchservice.domain.entities.GeocodedFile;
import com.example.searchservice.exceptions.UnsupportedFormatException;
import com.example.searchservice.services.GeocodedFileService;
import com.example.searchservice.services.GeocodingService;
import com.example.searchservice.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/geocoding")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class GeocodingController {
    private final GeocodingService geocodingService;
    private final GeocodedFileService geocodedFileService;
    private final NotificationService notificationService;

    @PostMapping
    public void geocoding(@RequestPart("file") MultipartFile file,
                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) throws IOException, UnsupportedFormatException {
        if (!file.getContentType().equals(ContentType.TEXT_PLAIN.getMimeType()))
            throw new UnsupportedFormatException("unsupported format " + file.getContentType());
        geocodingService.saveFile(file, principal.getAttribute("id"));
    }

    @GetMapping(value = "/notification-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> openStream(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return Flux.create(fluxSink -> {
            fluxSink.onCancel(() -> {
                notificationService.unsubscribe(principal.getAttribute("id"));
            });

            notificationService.subscribe(principal.getAttribute("id"), fluxSink);
        });
    }

    @GetMapping
    public List<GeocodedFile> getAllByPersonId(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return geocodedFileService.getAllByPersonId(principal.getAttribute("id"));
    }

    @ExceptionHandler
    public ResponseEntity<String> errorResponse(UnsupportedFormatException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
