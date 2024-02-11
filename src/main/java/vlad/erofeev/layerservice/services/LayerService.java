package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LayerService {
    private final LayerRepository layerRepository;

    public List<Layer> getAll() {
        return layerRepository.findAll();
    }

    public Layer getById(Long id) {
        Optional<Layer> optionalLayer = layerRepository.findById(id);
        if (optionalLayer.isEmpty())
            throw new ObjectNotFoundException("Layer not found", id);
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
    public Layer edit(Layer layer, String id) {
        long[] ids = PropsMapper.decodeId(id);
        if (ids.length == 0 || !layerRepository.existsById(ids[0]))
            throw new ObjectNotFoundException("Layer not found", (Object) id);
        layer.setId(ids[0]);
        return layerRepository.save(layer);
    }
}
