package com.example.searchservice.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    private Long id;
    private Long code_id;
    private String name;
    private String addr_country;
    private String addr_city;
    private String addr_street;
    private String addr_housenumber;
}
