package com.example.loadservice.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import org.wololo.geojson.Feature;

import java.util.Date;

@Getter
@Setter
public class ZoneDto {
    private String zoneId;
    private String personId;
    private String name;
    private Date creationDate;
    private Feature geometry;
}
