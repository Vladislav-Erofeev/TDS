package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.ErrorResponse;
import vlad.erofeev.layerservice.domain.dto.NewPropertyDTO;
import vlad.erofeev.layerservice.domain.dto.PropertyDTO;
import vlad.erofeev.layerservice.domain.dto.PropertyListItemDTO;
import vlad.erofeev.layerservice.domain.entities.Property;
import vlad.erofeev.layerservice.services.PropertyService;
import vlad.erofeev.layerservice.services.mappers.PropertyMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/properties")
@Slf4j
public class PropertyController {
    private final PropertyService propertyService;
    private final PropertyMapper propertyMapper = PropertyMapper.INSTANCE;

    @PostMapping
    public PropertyDTO save(@RequestBody NewPropertyDTO newPropertyDTO) {
        log.info("POST /properties {}", newPropertyDTO);
        Property property = propertyMapper.convertToProperty(newPropertyDTO);
        Long layerId = PropsMapper.decodeId(newPropertyDTO.getLayerId());
        return propertyMapper.convertToPropertyDTO(propertyService.save(property, layerId));
    }

    @GetMapping
    public List<PropertyListItemDTO> getAll() {
        log.info("GET /properties");
        return propertyService.getAll().stream()
                .map(propertyMapper::convertToPropertyListItemDTO).collect(Collectors.toList());
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
