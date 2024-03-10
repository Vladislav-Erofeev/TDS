package vlad.erofeev.layerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import vlad.erofeev.layerservice.controllers.LayerController;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LayerController.class)
public class LayerControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCannotDecodeIdInGet_thenThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L) + "l";
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(result -> {
            Assertions.assertEquals(400, result.getResponse().getStatus());
            Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
                throw Objects.requireNonNull(result.getResolvedException());
            });

            Assertions.assertTrue(result.getResponse().getContentAsString().contains("Illegal id format id="));
        });
    }

    @Test
    public void whenCannotDecodeIdInEdit_thenThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L) + "k";
        Layer layer = new Layer();
        layer.setName("name 1");
        String content = objectMapper.writeValueAsString(layer);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(result -> {
            Assertions.assertEquals(400, result.getResponse().getStatus());
            Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
                throw Objects.requireNonNull(result.getResolvedException());
            });
            Assertions.assertTrue(result.getResponse().getContentAsString().contains("Illegal id format id="));
        });
    }

    @Test
    public void whenCannotDecodeIdInDelete_thenThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L) + "k";
        Layer layer = new Layer();
        layer.setName("name 1");
        String content = objectMapper.writeValueAsString(layer);

        mockMvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(result -> {
                    Assertions.assertEquals(400, result.getResponse().getStatus());
                    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
                        throw Objects.requireNonNull(result.getResolvedException());
                    });
                    Assertions.assertTrue(result.getResponse().getContentAsString().contains("Illegal id format id="));
                });
    }

    @Test
    public void whenIdIsNotExistInPatch_thenThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L);
        Layer layer = new Layer();
        layer.setName("name 1");
        String content = objectMapper.writeValueAsString(layer);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(result -> {
                    Assertions.assertEquals(404, result.getResponse().getStatus());
                    Assertions.assertThrowsExactly(ObjectNotFoundException.class, () -> {
                        throw Objects.requireNonNull(result.getResolvedException());
                    });
                    Assertions.assertTrue(result.getResponse()
                            .getContentAsString().contains("No row with the given identifier exists"));
                });
    }

    @Test
    public void whenIdIsNotExistInGet_thenThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L);
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(result -> {
            Assertions.assertThrows(ObjectNotFoundException.class, () -> {
                throw Objects.requireNonNull(result.getResolvedException());
            });
            Assertions.assertTrue(result.getResponse()
                    .getContentAsString().contains("No row with the given identifier exists"));
            Assertions.assertEquals(404, result.getResponse().getStatus());
        });
    }

    @Test
    public void whenReturnObject_thenIdIsNotOriginal() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(2L);
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(result -> {
            HashMap res = objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
            Assertions.assertNotEquals(2L, res.get("id"));
        });
    }

    @TestConfiguration
    protected static class LayerControllerTestConfiguration {
        @MockBean
        protected LayerRepository layerRepository;

        @PostConstruct
        public void init() {
            Mockito.when(layerRepository.findById(1L)).thenReturn(Optional.empty());
            Layer layer = new Layer();
            layer.setId(2L);
            Mockito.when(layerRepository.findById(2L)).thenReturn(Optional.of(layer));
        }

        @Bean
        public LayerService layerService() {
            return new LayerService(layerRepository);
        }
    }
}
