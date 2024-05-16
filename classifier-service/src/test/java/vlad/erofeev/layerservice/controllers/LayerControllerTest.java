package vlad.erofeev.layerservice.controllers;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import vlad.erofeev.layerservice.domain.dto.LayerDetailsDto;
import vlad.erofeev.layerservice.domain.entities.Layer;
import vlad.erofeev.layerservice.repositories.LayerRepository;
import vlad.erofeev.layerservice.services.LayerService;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LayerController.class)
@ExtendWith(SpringExtension.class)
class LayerControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private LayerRepository layerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getById_LayerNotFound_ThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L);
        Mockito.when(layerRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().is(404))
                .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("Layer#1")))
                .andExpect(result -> assertEquals(ObjectNotFoundException.class, result.getResolvedException().getClass()));
    }

    @Test
    void patchById_LayerNotFound_ThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L);
        LayerDetailsDto layerDetailsDto = new LayerDetailsDto();
        layerDetailsDto.setName("layer");
        layerDetailsDto.setHname("layer");
        layerDetailsDto.setGeometryType("POLYGON");
        Mockito.when(layerRepository.findById(1L)).thenReturn(Optional.empty());
        String content = objectMapper.writeValueAsString(layerDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("No row with the given identifier exists: [Layer#1]"))
                .andExpect(result -> assertEquals(ObjectNotFoundException.class, result.getResolvedException().getClass()));
    }

    @Test
    void patchById_NullFields_ThrowException() throws Exception {
        String uri = "/layers/" + PropsMapper.encodeId(1L);
        String layerDetailsDto = objectMapper.writeValueAsString(new LayerDetailsDto());

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(layerDetailsDto))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Name cannot be null"))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));
    }

    @Test
    void save_NullFields_ThrowException() throws Exception {
        String uri = "/layers";
        String layerDetailsDto = objectMapper.writeValueAsString(new LayerDetailsDto());

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(layerDetailsDto))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Name cannot be null"))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));
    }

    @Test
    void save_NewLayer_SavedLayer() throws Exception {
        String uri = "/layers";
        LayerDetailsDto layerDetailsDto = new LayerDetailsDto();
        layerDetailsDto.setName("layer");
        layerDetailsDto.setHname("layer");
        layerDetailsDto.setGeometryType("POLYGON");
        String content = objectMapper.writeValueAsString(layerDetailsDto);

        var capture = ArgumentCaptor.forClass(Layer.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk());

        Mockito.verify(layerRepository).save(capture.capture());
        assertNotNull(capture.getValue().getCreationDate());
        assertNull(capture.getValue().getId());
    }

    @TestConfiguration
    static class LayerControllerTestConfiguration {
        @MockBean
        private LayerRepository layerRepository;

        @Bean
        public LayerService layerService() {
            return new LayerService(layerRepository);
        }
    }
}