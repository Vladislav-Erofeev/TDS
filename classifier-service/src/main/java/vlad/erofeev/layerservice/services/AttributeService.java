package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.dto.AttributeDto;
import vlad.erofeev.layerservice.domain.entities.Attribute;
import vlad.erofeev.layerservice.repositories.AttributeRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttributeService {
    private final AttributeRepository attributeRepository;

    @Transactional
    public Attribute save(Attribute attribute) {
        attribute.setId(null);
        attribute.setCreationDate(new Date());
        return attributeRepository.save(attribute);
    }

    public List<Attribute> getAll() {
        return attributeRepository.findAll();
    }

    public Attribute getById(Long id) {
        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
        if (optionalAttribute.isEmpty())
            throw new ObjectNotFoundException(id, "Attribute");
        return optionalAttribute.get();
    }

    @Transactional
    public Attribute patchById(Attribute attribute, Long id) {
        Attribute oldAttribute = getById(id);
        attribute.setId(id);
        attribute.setCreationDate(oldAttribute.getCreationDate());
        return attributeRepository.save(attribute);
    }

    @Transactional
    public void deleteById(Long id) {
        attributeRepository.deleteById(id);
    }
}
