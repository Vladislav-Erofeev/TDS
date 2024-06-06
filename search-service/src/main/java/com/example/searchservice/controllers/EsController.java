package com.example.searchservice.controllers;

import com.example.searchservice.messages.Item;
import com.example.searchservice.services.EsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EsController {
    private final EsService esService;

    @GetMapping("/search")
    public List<Item> search(@RequestParam("query") String query) throws IOException {
        log.info("GET /search query={}", query);
        return esService.search(query);
    }

    @GetMapping(value = "/search", params = {"query", "codes"})
    public List<Item> searchWithFilters(@RequestParam("query") String query, @RequestParam("codes") List<Long> codes) throws IOException {
        log.info("GET /search query={} codes={}", query, codes);
        return esService.searchByQueryAndCodesIn(query, codes);
    }
}
