package com.example.geodata.domain.dto;


import org.wololo.geojson.Feature;

import java.util.Map;

public record NewItemDto(String id, Map<String, Object> properties, CodeDto code, Feature feature) {
}
