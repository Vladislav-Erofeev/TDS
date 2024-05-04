package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "layer")
public class LayerService {
    private final LayerRepository layerRepository;

    public List<Layer> getAll() {
        return layerRepository.findAll();
    }

    @CachePut(key = "#result.id")
    @Transactional
    public Layer save(Layer layer) {
        layer.setId(null);
        layer.setCreationDate(new Date());
        return layerRepository.save(layer);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Layer getById(Long id) throws ObjectNotFoundException {
        Optional<Layer> optionalLayer = layerRepository.findById(id);
        if (optionalLayer.isEmpty())
            throw new ObjectNotFoundException(id, "Layer");
        return optionalLayer.get();
    }

    @CachePut(key = "#id", unless = "#result == null")
    @Transactional
    public Layer patchById(Layer layer, Long id) {
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

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteById(Long id) {
        layerRepository.deleteById(id);
    }
}
