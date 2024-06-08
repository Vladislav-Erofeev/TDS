package com.example.searchservice.controllers;

import com.example.searchservice.entities.GeocodedFile;
import com.example.searchservice.services.GeocodedFileService;
import com.example.searchservice.services.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/geocoding")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class GeocodingController {
    private final GeocodingService geocodingService;
    private final GeocodedFileService geocodedFileService;

    @PostMapping
    public void geocoding(@RequestPart("file") MultipartFile file,
                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) throws IOException {
        geocodingService.saveFile(file, principal.getAttribute("id"));
    }

    @GetMapping
    public List<GeocodedFile> getAllByPersonId(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return geocodedFileService.getAllByPersonId(principal.getAttribute("id"));
    }
}
