package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.CodeDto;
import vlad.erofeev.layerservice.domain.dto.CodeListItemDto;
import vlad.erofeev.layerservice.domain.entities.Code;

@Mapper(uses = {LayerMapper.class, PropsMapper.class})
public interface CodeMapper {
    CodeMapper INSTANCE = Mappers.getMapper(CodeMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    CodeDto toDto(Code code);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Code toEntity(CodeDto codeDto);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    CodeListItemDto toListItem(Code code);
}
