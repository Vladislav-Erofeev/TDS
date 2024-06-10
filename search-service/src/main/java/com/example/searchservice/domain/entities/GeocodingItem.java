package com.example.searchservice.domain.entities;

import com.example.searchservice.domain.dtos.ItemDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodingItem {
    private String request;
    private ItemDto item;
}
