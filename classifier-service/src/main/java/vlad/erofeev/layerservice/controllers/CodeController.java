package vlad.erofeev.layerservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.layerservice.domain.dto.CodeDetailsDto;
import vlad.erofeev.layerservice.domain.dto.CodeDto;
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
public class CodeController {
    private final CodeService codeService;
    private final CodeMapper codeMapper = CodeMapper.INSTANCE;

    @GetMapping
    public List<CodeDto> getAll() {
        log.info("GET /codes");
        List<Code> codes = codeService.getAll();
        return codes.stream().map(codeMapper::toListItem).collect(Collectors.toList());
    }

    @PostMapping
    public CodeDetailsDto save(@RequestBody CodeDetailsDto codeDetailsDto) {
        log.info("POST /codes {}", codeDetailsDto);
        Code code = codeService.save(codeMapper.toEntity(codeDetailsDto));
        return codeMapper.toDto(code);
    }

    @GetMapping("/{id}")
    public CodeDetailsDto getById(@PathVariable("id") String id) {
        log.info("GET /codes/{}", id);
        return codeMapper.toDto(codeService.getById(PropsMapper.decodeId(id)));
    }

    @PatchMapping("/{id}")
    public CodeDetailsDto patchById(@PathVariable("id") String id,
                                    @RequestBody CodeDetailsDto codeDetailsDto) {
        log.info("PATCH /codes/{} {}", codeDetailsDto, id);
        return codeMapper.toDto(codeService.patchById(codeMapper.toEntity(codeDetailsDto), PropsMapper.decodeId(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        log.info("DELETE /codes/{}", id);
        codeService.deleteById(PropsMapper.decodeId(id));
    }
}
