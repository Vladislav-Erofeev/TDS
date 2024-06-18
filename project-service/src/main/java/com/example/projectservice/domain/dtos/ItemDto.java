package com.example.projectservice.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import org.wololo.geojson.Feature;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ItemDto {
    private Map<String, Object> properties;
    private String id;
    private String personId;
    private String codeId;
    private String projectId;
    private String name;
    private String addrCountry;
    private String addrCity;
    private String addrStreet;
    private String addrHousenumber;
    private Date creationDate;
    private Date modifiedDate;
    private Feature feature;
}
