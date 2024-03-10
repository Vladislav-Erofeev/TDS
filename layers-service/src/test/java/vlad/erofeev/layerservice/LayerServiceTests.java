package vlad.erofeev.layerservice;

import jakarta.annotation.PostConstruct;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;
import vlad.erofeev.layerservice.services.LayerService;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class LayerServiceTests {
    @MockBean
    private LayerRepository layerRepository;

    private LayerService layerService;

    @PostConstruct
    public void init() {
        layerService = new LayerService(layerRepository);
    }

    @Test
    public void whenGetAll_thenReturnList() {
        List<Layer> layers = List.of(new Layer(), new Layer(), new Layer());
        Mockito.when(layerRepository.findAll()).thenReturn(layers);
        Assertions.assertEquals(layers.size(), layerService.getAll().size());
    }

    @Test
    public void whenObjectIsEmpty_thenThrowException() {
        Optional<Layer> optionalLayer = Optional.empty();
        Mockito.when(layerRepository.findById(1L)).thenReturn(optionalLayer);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            layerService.getById(1L);
        });
    }

    @Test
    public void whenTryToEditNotExistObject_thenThrowException() {
        Optional<Layer> optionalLayer = Optional.empty();
        Layer layer = new Layer();
        layer.setId(1L);
        layer.setName("Name");
        Mockito.when(layerRepository.findById(1L)).thenReturn(optionalLayer);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            layerService.edit(layer);
        });
    }
}
