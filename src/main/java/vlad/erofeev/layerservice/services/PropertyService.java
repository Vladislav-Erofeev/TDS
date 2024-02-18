package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.domain.entities.Property;
import vlad.erofeev.layerservice.repositories.PropertyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final LayerService layerService;

    @Transactional
    public Property save(Property property, Long layerId) throws ObjectNotFoundException {
        Layer layer = layerService.getById(layerId);
        property.setLayer(layer);
        return propertyRepository.save(property);
    }

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }
}
