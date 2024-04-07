package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.AttributeDetailsDto;
import vlad.erofeev.layerservice.domain.dto.AttributeDto;
import vlad.erofeev.layerservice.services.AttributeService;
import vlad.erofeev.layerservice.services.mappers.AttributeMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/attributes")
public class AttributeController {
    private final AttributeService attributeService;
    private final AttributeMapper attributeMapper = AttributeMapper.INSTANCE;

    @GetMapping
    public List<AttributeDto> getAll() {
        log.info("GET /attributes");
        return attributeService.getAll().stream().map(attributeMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public AttributeDetailsDto save(@RequestBody AttributeDetailsDto attributeDto) {
        log.info("POST /attributes {}", attributeDto);
        return attributeMapper.toDetailsDto(attributeService.save(attributeMapper.toEntity(attributeDto)));
    }

    @GetMapping("/{id}")
    public AttributeDetailsDto getById(@PathVariable("id") String id) {
        log.info("GET /attributes/{}", id);
        return attributeMapper.toDetailsDto(attributeService.getById(PropsMapper.decodeId(id)));
    }

    @PatchMapping("/{id}")
    public AttributeDetailsDto save(@PathVariable("id") String id,
                                    @RequestBody AttributeDetailsDto attributeDto) {
        log.info("PATCH /attributes/{} {}", id, attributeDto);
        return attributeMapper.toDetailsDto(attributeService.patchById(attributeMapper
                .toEntity(attributeDto), PropsMapper.decodeId(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /attributes/{}", id);
        attributeService.deleteById(PropsMapper.decodeId(id));
    }
}
