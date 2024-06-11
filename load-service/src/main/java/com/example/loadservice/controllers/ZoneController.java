package com.example.loadservice.controllers;

import com.example.loadservice.services.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/zones")
@PreAuthorize("isAuthenticated()")
public class ZoneController {
    private final ZoneService zoneService;
}
