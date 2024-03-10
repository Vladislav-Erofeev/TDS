package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.dto.CodeItemDTO;
import vlad.erofeev.layerservice.domain.entities.Code;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.CodeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CodeService {
    private final CodeRepository codeRepository;
    private final LayerService layerService;

    public List<Code> getAll() {
        return codeRepository.findAll();
    }

    @Transactional
    public Code save(Code code, Long layerId) {
        Layer layer = layerService.getById(layerId);
        code.setLayer(layer);
        return codeRepository.save(code);
    }

    @Transactional
    public void deleteById(Long id) {
        codeRepository.deleteById(id);
    }

    @Transactional
    public Code edit(Code code) {
        Optional<Code> optionalCode = codeRepository.findById(code.getId());
        if (optionalCode.isEmpty()) {
            log.error("Code not found id={}", code.getId());
            throw new ObjectNotFoundException("Code not found", code.getId());
        }
        code.setLayer(optionalCode.get().getLayer());
        return codeRepository.save(code);
    }
}
