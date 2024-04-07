package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.CodeDetailsDto;
import vlad.erofeev.layerservice.domain.dto.CodeDto;
import vlad.erofeev.layerservice.domain.entities.Code;

@Mapper(uses = {LayerMapper.class, PropsMapper.class})
public interface CodeMapper {
    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    CodeDetailsDto toDto(Code code);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Code toEntity(CodeDetailsDto codeDetailsDto);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    CodeDto toListItem(Code code);
}
