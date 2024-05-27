package com.example.searchservice.controllers;

import com.example.searchservice.services.EsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EsController {
    private final EsService esService;

    @PostMapping
    public String save(@RequestParam("title") String title, @RequestParam("body") String body) throws IOException {
        String uuid = UUID.randomUUID().toString();
        esService.save(uuid, title, body);
        return uuid;
    }

    @GetMapping
    public List<EsService.Geodata> getByTitle(@RequestParam("title") String title) throws IOException {
        return esService.search(title);
    }

    @DeleteMapping
    public void deleteById(@RequestParam("id") String id) throws IOException {
        esService.deleteById(id);
    }

    @PutMapping
    public void updateById(@RequestParam("id") String id, @RequestParam("title") String title, @RequestParam("body") String body) throws IOException {
        esService.updateById(id, body, title);
    }
}
