package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Code;
import vlad.erofeev.layerservice.repositories.CodeRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeService {
    private final CodeRepository codeRepository;

    @Transactional
    public Code save(Code code) {
        Objects.requireNonNull(code.getLayer(), "Layer cannot be null");
        Objects.requireNonNull(code.getCode(), "Code cannot be null");
        Objects.requireNonNull(code.getName(), "Name cannot be null");

        code.setId(null);
        code.setCreationDate(new Date());
        return codeRepository.save(code);
    }

    public Code getById(Long id) {
        Optional<Code> optionalCode = codeRepository.findById(id);
        if (optionalCode.isEmpty())
            throw new ObjectNotFoundException(id, "Code");
        return optionalCode.get();
    }

    public List<Code> getAll() {
        return codeRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        codeRepository.deleteById(id);
    }

    @Transactional
    public Code patchById(Code code, Long id) {
        Objects.requireNonNull(code.getLayer(), "Layer cannot be null");
        Objects.requireNonNull(code.getCode(), "Code cannot be null");
        Objects.requireNonNull(code.getName(), "Name cannot be null");

        Code oldCode = getById(id);
        code.setId(oldCode.getId());
        code.setCreationDate(oldCode.getCreationDate());
        return codeRepository.save(code);
    }
}
