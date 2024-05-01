package com.example.geodata.controllers;

import com.example.geodata.domain.dto.ErrorResponse;
import com.example.geodata.domain.dto.ItemDto;
import com.example.geodata.domain.dto.NewItemDto;
import com.example.geodata.domain.entities.Item;
import com.example.geodata.mappers.ItemMapper;
import com.example.geodata.mappers.PropsMapper;
import com.example.geodata.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
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
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class ObjectController {
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;
    private final ItemService itemService;

    @PostMapping
    public void add(@RequestBody NewItemDto newItemDto,
                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        Objects.requireNonNull(newItemDto.code(), "Code не может быть пустым");
        Objects.requireNonNull(newItemDto.feature(), "Геометрия не может быть пустой");
        switch (newItemDto.feature().getGeometry().getType()) {
            case "Polygon" -> itemService.save(itemMapper.toPolygon(newItemDto), principal.getAttribute("id"));
            case "LineString" -> itemService.save(itemMapper.toLine(newItemDto), principal.getAttribute("id"));
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Feature> getAll() {
        return itemService.getAll().stream().map(this::toFeature).toList();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(params = {"added"})
    public List<ItemDto> getAllAddedUsers(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                                          @RequestParam(name = "added") Boolean added) {
        return itemService.getAllByPersonId(principal.getAttribute("id"))
                .stream().map(itemMapper::toDto).toList();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable("id") String id) {
        return itemMapper.toDto(itemService.getById(PropsMapper.decodeId(id)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/{id}/check")
    public void checkObject(@PathVariable("id") String id) {
        itemService.setCheckedById(PropsMapper.decodeId(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        itemService.deleteById(PropsMapper.decodeId(id));
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
