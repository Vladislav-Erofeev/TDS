package com.example.searchservice.controllers;

import com.example.searchservice.dos.ItemDto;
import com.example.searchservice.messages.Item;
import com.example.searchservice.services.EsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class EsController {
    private final EsService esService;
    private final Hashids hashids = new Hashids("TESTSALT", 4);

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam("query") String query) throws IOException {
        log.info("GET /search query={}", query);
        return esService.search(query).stream().map(this::toDto).toList();
    }

    @GetMapping(value = "/search", params = {"query", "codes"})
    public List<ItemDto> searchWithFilters(@RequestParam("query") String query, @RequestParam("codes") List<Long> codes) throws IOException {
        log.info("GET /search query={} codes={}", query, codes);
        return esService.searchByQueryAndCodesIn(query, codes).stream().map(this::toDto).toList();
    }

    private ItemDto toDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(hashids.encode(item.getId()));
        System.out.println(hashids.encode(item.getId()));
        itemDto.setName(item.getName());
        itemDto.setAddr_city(item.getAddr_city());
        itemDto.setAddr_country(item.getAddr_country());
        itemDto.setAddr_street(item.getAddr_street());
        itemDto.setAddr_housenumber(item.getAddr_housenumber());
        return itemDto;
    }
}
