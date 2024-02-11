package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.ErrorResponse;
import vlad.erofeev.layerservice.domain.dto.LayerDTO;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.LayerMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import javax.management.ObjectName;
import java.util.List;
import java.util.Objects;
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

    @PostMapping
    public LayerDTO save(@RequestBody LayerDTO layerDTO) {
        Layer layer = layerMapper.convertToLayer(layerDTO);
        layer.setId(null);
        return layerMapper.convertToLayerDTO(layerService.save(layer));
    }

    @GetMapping("/{id}")
    public LayerDTO getById(@PathVariable("id") String id) {
        long[] ids = PropsMapper.decodeId(id);
        return layerMapper.convertToLayerDTO(layerService.getById(ids[0]));
    }

    @PatchMapping("/{id}")
    public LayerDTO edit(@PathVariable("id") String id, @RequestBody LayerDTO layerDTO) {
        Layer layer = layerMapper.convertToLayer(layerDTO);
        return layerMapper.convertToLayerDTO(layerService.edit(layer, id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        long[] ids = PropsMapper.decodeId(id);
        layerService.deleteById(ids[0]);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> objectNotFound(ObjectNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
