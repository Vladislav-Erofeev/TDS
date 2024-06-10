package com.example.searchservice.entities;

import com.example.searchservice.dtos.ItemDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodingItem {
    private String request;
    private ItemDto item;
}
