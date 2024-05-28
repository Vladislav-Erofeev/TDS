package com.example.geodata.domain.dto;


import org.wololo.geojson.Feature;

import java.util.Map;

public record NewItemDto(String id, String name, String addrCountry, String addrCity, String addrStreet,
                         String addrHousenumber, Map<String, Object> properties, CodeDto code, Feature feature) {
}
