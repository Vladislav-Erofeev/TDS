package vlad.erofeev.layerservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.LayerDetailsDto;
import vlad.erofeev.layerservice.domain.dto.LayerDto;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.LayerDetailsMapper;
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
    private final LayerDetailsMapper layerDetailsMapper = LayerDetailsMapper.INSTANCE;

    @GetMapping
    public List<LayerDto> getAll() {
        log.info("GET /layers");
        return layerService.getAll().stream().map(layerMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public LayerDetailsDto save(@RequestBody LayerDetailsDto layerDto) {
        log.info("POST /layers {}", layerDto);
        return layerDetailsMapper.toDetailsDto(layerService.save(layerDetailsMapper.toEntity(layerDto)));
    }

    @GetMapping("/{id}")
    public LayerDetailsDto getById(@PathVariable("id") String id) {
        log.info("GET /layers/{}", id);
        return layerDetailsMapper.toDetailsDto(layerService.getById(PropsMapper.decodeId(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /layers/{}", id);
        layerService.deleteById(PropsMapper.decodeId(id));
    }
}
