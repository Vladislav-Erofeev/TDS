package vlad.erofeev.layerservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.ErrorResponse;
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

    @PatchMapping("/{id}")
    public LayerDetailsDto patchById(@PathVariable("id") String id,
                                     @RequestBody LayerDetailsDto layerDetailsDto) {
        log.info("PATCH /layers/{}", id);
        return layerDetailsMapper.toDetailsDto(layerService.patchById(layerDetailsMapper.toEntity(layerDetailsDto), PropsMapper.decodeId(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /layers/{}", id);
        layerService.deleteById(PropsMapper.decodeId(id));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> objectNotFound(ObjectNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> nullFields(NullPointerException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
