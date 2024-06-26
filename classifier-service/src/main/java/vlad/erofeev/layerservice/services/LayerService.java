package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LayerService {
    private final LayerRepository layerRepository;

    public List<Layer> getAll() {
        return layerRepository.findAll();
    }

    @Transactional
    public Layer save(Layer layer) {
        Objects.requireNonNull(layer.getName(), "Name cannot be null");
        Objects.requireNonNull(layer.getHname(), "hName cannot be null");
        Objects.requireNonNull(layer.getGeometryType(), "GeometryType cannot be null");

        layer.setId(null);
        layer.setCreationDate(new Date());
        return layerRepository.save(layer);
    }

    public Layer getById(Long id) throws ObjectNotFoundException {
        Optional<Layer> optionalLayer = layerRepository.findById(id);
        if (optionalLayer.isEmpty())
            throw new ObjectNotFoundException(id, "Layer");
        return optionalLayer.get();
    }

    @Transactional
    public Layer patchById(Layer layer, Long id) {
        Objects.requireNonNull(layer.getName(), "Name cannot be null");
        Objects.requireNonNull(layer.getHname(), "hName cannot be null");
        Objects.requireNonNull(layer.getGeometryType(), "GeometryType cannot be null");

        Layer oldLayer = getById(id);
        layer.setId(oldLayer.getId());
        layer.setCreationDate(oldLayer.getCreationDate());
        layer.setCodes(oldLayer.getCodes());
        layer.getAttributes().forEach(attribute -> {
            attribute.getLayers().add(layer);
        });
        layerRepository.save(layer);
        return layer;
    }

    @Transactional
    public void deleteById(Long id) {
        layerRepository.deleteById(id);
    }
}
