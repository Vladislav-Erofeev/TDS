package vlad.erofeev.layerservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.LayerDto;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.LayerMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/layers")
@RequiredArgsConstructor
@Slf4j
public class LayerController {
    private final LayerService layerService;
    private final LayerMapper layerMapper = LayerMapper.INSTANCE;

    @GetMapping
    public List<LayerDto> getAll() {
        log.info("GET /layers");
        return layerService.getAll().stream().map(layerMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public LayerDto save(@RequestBody LayerDto layerDto) {
        log.info("POST /layers {}", layerDto);
        return layerMapper.toDto(layerService.save(layerMapper.toEntity(layerDto)));
    }

    @GetMapping("/{id}")
    public LayerDto getById(@PathVariable("id") String id) {
        log.info("GET /layers/{}", id);
        return layerMapper.toDto(layerService.getById(PropsMapper.decodeId(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /layers/{}", id);
        layerService.deleteById(PropsMapper.decodeId(id));
    }
}
