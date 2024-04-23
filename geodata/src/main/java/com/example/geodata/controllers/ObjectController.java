package com.example.geodata.controllers;

import com.example.geodata.domain.dto.ErrorResponse;
import com.example.geodata.domain.dto.NewItemDto;
import com.example.geodata.domain.entities.Item;
import com.example.geodata.mappers.ItemMapper;
import com.example.geodata.mappers.PropsMapper;
import com.example.geodata.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.Feature;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/objects")
@RequiredArgsConstructor
public class ObjectController {
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;
    private final ItemService itemService;

    @PostMapping
    public void add(@RequestBody NewItemDto newItemDto) {
        Objects.requireNonNull(newItemDto.code(), "Code не может быть пустым");
        Objects.requireNonNull(newItemDto.feature(), "Геометрия не может быть пустой");
        switch (newItemDto.feature().getGeometry().getType()) {
            case "Polygon" -> itemService.save(itemMapper.toPolygon(newItemDto));
            case "LineString" -> itemService.save(itemMapper.toLine(newItemDto));
        }
    }

    @GetMapping
    public List<Feature> getAll() {
        return itemService.getAll().stream().map(this::toFeature).toList();
    }

    private Feature toFeature(Item item) {
        Map<String, Object> props = new HashMap<>();
        props.put("id", PropsMapper.encodeId(item.getId()));
        return new Feature(new GeoJSONWriter().write(item.getGeometry()), props);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> NullPointerException(NullPointerException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
