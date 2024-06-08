package com.example.searchservice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodingItem {
    private String request;
    private Item item;
}
