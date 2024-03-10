package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.CodeItemDTO;
import vlad.erofeev.layerservice.domain.dto.ErrorResponse;
import vlad.erofeev.layerservice.domain.dto.NewCodeItemDTO;
import vlad.erofeev.layerservice.domain.entities.Code;
import vlad.erofeev.layerservice.services.CodeService;
import vlad.erofeev.layerservice.services.mappers.CodeMapper;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class CodeController {
    private final CodeService codeService;
    private final CodeMapper codeMapper = CodeMapper.INSTANCE;

    @GetMapping
    public List<CodeItemDTO> getAll() {
        log.info("GET /code");
        return codeService.getAll().stream().map(codeMapper::convertToCodeItemDTO).collect(Collectors.toList());
    }

    @PostMapping
    public CodeItemDTO save(@RequestBody NewCodeItemDTO newCodeItemDTO) {
        log.info("POST /code {}", newCodeItemDTO.toString());
        Code code = codeMapper.convertToCode(newCodeItemDTO);
        Long layerId = PropsMapper.decodeId(newCodeItemDTO.getLayerId());
        return codeMapper.convertToCodeItemDTO(codeService.save(code, layerId));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /code/{}", id);
        codeService.deleteById(PropsMapper.decodeId(id));
    }

    @PatchMapping("/{id}")
    public CodeItemDTO editCode(@PathVariable("id") String id, @RequestBody NewCodeItemDTO newCodeItemDTO) {
        log.info("PATCH /code/{}", id);
        Code code = codeMapper.convertToCode(newCodeItemDTO);
        code.setId(PropsMapper.decodeId(id));
        return codeMapper.convertToCodeItemDTO(codeService.edit(code));
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
