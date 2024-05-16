package vlad.erofeev.layerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
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
import vlad.erofeev.layerservice.domain.dto.AttributeDetailsDto;
import vlad.erofeev.layerservice.domain.entities.Attribute;
import vlad.erofeev.layerservice.repositories.AttributeRepository;
import vlad.erofeev.layerservice.services.AttributeService;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttributeController.class)
@ExtendWith(SpringExtension.class)
class AttributeControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getById_ObjectNotFound_ThrowException() throws Exception {
        String uri = "/attributes/" + PropsMapper.encodeId(1L);
        Mockito.when(attributeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().is(404))
                .andExpect(result -> assertEquals(ObjectNotFoundException.class, result.getResolvedException().getClass()));
    }

    @Test
    void patchById_ObjectNotFound_ThrowException() throws Exception {
        String uri = "/attributes/" + PropsMapper.encodeId(1L);
        AttributeDetailsDto attributeDetailsDto = new AttributeDetailsDto();
        attributeDetailsDto.setName("name");
        attributeDetailsDto.setHname("name");
        attributeDetailsDto.setDataType("INTEGER");
        Mockito.when(attributeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        String content = objectMapper.writeValueAsString(attributeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(404))
                .andExpect(result -> assertEquals(ObjectNotFoundException.class, result.getResolvedException().getClass()));
    }

    @Test
    void patchById_NullFields_ThrowException() throws Exception {
        String uri = "/attributes/" + PropsMapper.encodeId(1L);
        AttributeDetailsDto attributeDetailsDto = new AttributeDetailsDto();
        String content = objectMapper.writeValueAsString(attributeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));

        attributeDetailsDto.setName("name");
        content = objectMapper.writeValueAsString(attributeDetailsDto);
        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));

        attributeDetailsDto.setHname("name");
        content = objectMapper.writeValueAsString(attributeDetailsDto);
        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));
    }

    @Test
    void save_NullFields_ThrowException() throws Exception {
        String uri = "/attributes";
        AttributeDetailsDto attributeDetailsDto = new AttributeDetailsDto();
        String content = objectMapper.writeValueAsString(attributeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));

        attributeDetailsDto.setName("name");
        content = objectMapper.writeValueAsString(attributeDetailsDto);
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));

        attributeDetailsDto.setHname("name");
        content = objectMapper.writeValueAsString(attributeDetailsDto);
        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));
    }

    @Test
    void save_NewAttribute_SavedAttribute() throws Exception {
        String uri = "/attributes";
        AttributeDetailsDto attributeDetailsDto = new AttributeDetailsDto();
        attributeDetailsDto.setName("name");
        attributeDetailsDto.setHname("name");
        attributeDetailsDto.setDataType("name");
        String content = objectMapper.writeValueAsString(attributeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk());

        var captor = ArgumentCaptor.forClass(Attribute.class);
        Mockito.verify(attributeRepository).save(captor.capture());

        assertNull(captor.getValue().getId());
        assertNotNull(captor.getValue().getCreationDate());
    }

    @TestConfiguration
    static class AttributeControllerTestConfiguration {
        @MockBean
        private AttributeRepository attributeRepository;

        @Bean
        public AttributeService attributeService() {
            return new AttributeService(attributeRepository);
        }
    }

}