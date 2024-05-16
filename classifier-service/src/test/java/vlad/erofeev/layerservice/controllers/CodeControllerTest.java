package vlad.erofeev.layerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import vlad.erofeev.layerservice.domain.dto.CodeDetailsDto;
import vlad.erofeev.layerservice.domain.dto.LayerDto;
import vlad.erofeev.layerservice.domain.entities.Code;
import vlad.erofeev.layerservice.repositories.CodeRepository;
import vlad.erofeev.layerservice.services.CodeService;
import vlad.erofeev.layerservice.services.mappers.PropsMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CodeController.class)
@ExtendWith(SpringExtension.class)
class CodeControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getById_CodeNotExists_ThrowException() throws Exception {
        String uri = "/codes/" + PropsMapper.encodeId(1L);
        Mockito.when(codeRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().is(404))
                .andExpect(result -> assertEquals(ObjectNotFoundException.class, result.getResolvedException().getClass()));
    }

    @Test
    public void patchById_CodeNotExists_ThrowException() throws Exception {
        String uri = "/codes/" + PropsMapper.encodeId(1L);
        Mockito.when(codeRepository.findById(1L)).thenReturn(Optional.empty());
        CodeDetailsDto codeDetailsDto = new CodeDetailsDto();
        codeDetailsDto.setLayer(new LayerDto());
        codeDetailsDto.setCode(12312);
        codeDetailsDto.setName("name");
        String content = objectMapper.writeValueAsString(codeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(404))
                .andExpect(result -> assertEquals(ObjectNotFoundException.class, result.getResolvedException().getClass()));
    }

    @Test
    public void patchById_NullFields_ThrowException() throws Exception {
        String uri = "/codes/" + PropsMapper.encodeId(1L);
        CodeDetailsDto codeDetailsDto = new CodeDetailsDto();
        String content = objectMapper.writeValueAsString(codeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));
    }

    @Test
    public void save_NullFields_ThrowException() throws Exception {
        String uri = "/codes";
        CodeDetailsDto codeDetailsDto = new CodeDetailsDto();
        String content = objectMapper.writeValueAsString(codeDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().is(400))
                .andExpect(result -> assertEquals(NullPointerException.class, result.getResolvedException().getClass()));
    }

    @Test
    public void save_NewCode_SavedCode() throws Exception {
        String uri = "/codes";
        CodeDetailsDto codeDetailsDto = new CodeDetailsDto();
        codeDetailsDto.setName("name");
        codeDetailsDto.setCode(123);
        codeDetailsDto.setLayer(new LayerDto());
        String content = objectMapper.writeValueAsString(codeDetailsDto);

        var capture = ArgumentCaptor.forClass(Code.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk());

        Mockito.verify(codeRepository).save(capture.capture());
        assertNotNull(capture.getValue().getCreationDate());
        assertNull(capture.getValue().getId());
    }

    @TestConfiguration
    static class CodeControllerTestConfiguration {
        @MockBean
        CodeRepository codeRepository;

        @Bean
        public CodeService codeService() {
            return new CodeService(codeRepository);
        }
    }
}