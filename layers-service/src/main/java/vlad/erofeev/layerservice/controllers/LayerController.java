package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vlad.erofeev.layerservice.domain.dto.ErrorResponse;
import vlad.erofeev.layerservice.domain.dto.LayerDTO;
import vlad.erofeev.layerservice.domain.dto.LayerItemDTO;
import vlad.erofeev.layerservice.domain.dto.NewLayerDTO;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.LayerMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/layers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class LayerController {
    private final LayerService layerService;
    private final LayerMapper layerMapper = LayerMapper.INSTANCE;

    @GetMapping
    public List<LayerItemDTO> getAll() {
        log.info("GET /layer");
        return layerService.getAll().stream().map(layerMapper::convertToLayerItemDTO).collect(Collectors.toList());
    }

    @PostMapping
    public LayerItemDTO save(@RequestPart("data") NewLayerDTO layerDTO,
                             @RequestPart("image")MultipartFile multipartFile) {
        log.info("POST /layer {}", layerDTO.toString());
        Layer layer = layerMapper.convertToLayer(layerDTO);
        return layerMapper.convertToLayerItemDTO(layerService.save(layer, multipartFile));
    }

    @GetMapping("/{id}")
    public LayerDTO getById(@PathVariable("id") String id) {
        log.info("GET /layer/{}", id);
        return layerMapper.convertToLayerDTO(layerService.getById(PropsMapper.decodeId(id)));
    }

    @PatchMapping("/{id}")
    public LayerItemDTO edit(@PathVariable("id") String id, @RequestBody NewLayerDTO layerDTO) {
        log.info("PATCH /layer/{} {}", id, layerDTO.toString());
        Layer layer = layerMapper.convertToLayer(layerDTO);
        layer.setId(PropsMapper.decodeId(id));
        return layerMapper.convertToLayerItemDTO(layerService.edit(layer));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /layer/{}", id);
        layerService.deleteById(PropsMapper.decodeId(id));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> objectNotFound(ObjectNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> cannotDecodeId(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
