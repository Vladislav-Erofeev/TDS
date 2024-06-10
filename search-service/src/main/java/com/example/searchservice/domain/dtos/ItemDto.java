package com.example.searchservice.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.geometry.Point;

@Getter
@Setter
public class ItemDto {
    private String id;
    private Long code_id;
    private String name;
    private String addr_country;
    private String addr_city;
    private String addr_street;
    private String addr_housenumber;
    private CentroidDto centroid;
}
