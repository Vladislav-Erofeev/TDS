package com.example.geodata.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ItemDto {
    private String id;
    private String creationDate;
    private String code;
    private Boolean checked;
    private String name;
    private String addrCountry;
    private String addrCity;
    private String addrStreet;
    private String addrHousenumber;
    private Map<String, Object> properties;
}
