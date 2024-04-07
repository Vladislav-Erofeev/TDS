package vlad.erofeev.layerservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.LayerDto;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.LayerMapper;

import java.util.List;

@RestController
@RequestMapping("/layers")
@RequiredArgsConstructor
public class LayerController {
    private final LayerService layerService;
    private final LayerMapper layerMapper = LayerMapper.INSTANCE;

    @GetMapping
    public void getAll() {

    }

    @PostMapping
    public LayerDto save(@RequestBody LayerDto layerDto) {
        return layerMapper.toDto(layerService.save(layerMapper.toEntity(layerDto)));
    }
}
