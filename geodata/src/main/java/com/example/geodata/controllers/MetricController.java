package com.example.geodata.controllers;

import com.example.geodata.domain.dto.LineChartDto;
import com.example.geodata.services.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricController {
    private final MetricService metricService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/items")
    public LineChartDto getItemsCountByPersonIdGroupBuDay(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return metricService.getItemsCountByPersonIdGroupBuDay(principal.getAttribute("id"));
    }
}
