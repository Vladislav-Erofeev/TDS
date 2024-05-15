package com.example.geodata.services;

import com.example.geodata.domain.dto.LineChartDto;
import com.example.geodata.repositories.MetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricService {
    private final MetricRepository metricRepository;

    public LineChartDto getItemsCountByPersonIdGroupBuDay(Long personId) {
        return metricRepository.getItemsCountByPersonIdGroupBuDay(personId);
    }
}
