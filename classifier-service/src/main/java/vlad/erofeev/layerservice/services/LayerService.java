package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;

import java.util.Date;
import java.util.List;

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
        layer.setId(null);
        layer.setCreationDate(new Date());
        return layerRepository.save(layer);
    }
}
