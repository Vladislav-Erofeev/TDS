package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlad.erofeev.layerservice.domain.dto.LayerDTO;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.LayerMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/layer")
@RequiredArgsConstructor
public class LayerController {
    private final LayerService layerService;
    private final LayerMapper layerMapper = LayerMapper.INSTANCE;

    @GetMapping
    public List<LayerDTO> getAll() {
        return layerService.getAll().stream().map(layerMapper::convertToLayerDTO).collect(Collectors.toList());
    }
}
