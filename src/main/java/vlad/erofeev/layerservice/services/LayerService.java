package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LayerService {
    private final LayerRepository layerRepository;

    public List<Layer> getAll() {
        return layerRepository.findAll();
    }

    public Layer getById(Long id) throws ObjectNotFoundException {
        Optional<Layer> optionalLayer = layerRepository.findById(id);
        if (optionalLayer.isEmpty()) {
            log.error("Layer not found id={}", id);
            throw new ObjectNotFoundException("Layer not found", id);
        }
        return optionalLayer.get();
    }

    @Transactional
    public void deleteById(Long id) {
        layerRepository.deleteById(id);
    }

    @Transactional
    public Layer save(Layer layer) {
        return layerRepository.save(layer);
    }

    @Transactional
    public Layer edit(Layer layer) throws ObjectNotFoundException {
        try {
            getById(layer.getId());
            return layerRepository.save(layer);
        } catch (ObjectNotFoundException e) {
            throw e;
        }
    }
}
