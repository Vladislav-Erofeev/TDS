package vlad.erofeev.layerservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LayerService {
    private final LayerRepository layerRepository;

    public List<Layer> getAll() {
        return layerRepository.findAll();
    }
}
