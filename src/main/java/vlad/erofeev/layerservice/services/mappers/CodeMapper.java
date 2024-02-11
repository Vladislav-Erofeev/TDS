package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.CodeItemDTO;
import vlad.erofeev.layerservice.domain.dto.NewCodeItemDTO;
import vlad.erofeev.layerservice.domain.entities.Code;
import vlad.erofeev.layerservice.domain.entities.Layer;

@Mapper(uses = {PropsMapper.class})
public interface CodeMapper {
    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    CodeItemDTO convertToCodeItemDTO(Code code);

    Code convertToCode(NewCodeItemDTO newCodeItemDTO);
}
