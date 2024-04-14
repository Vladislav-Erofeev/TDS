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
    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "dateToString")
    CodeDetailsDto toDto(Code code);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "stringToDate")
    Code toEntity(CodeDetailsDto codeDetailsDto);

    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "dateToString")
    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    CodeDto toListItem(Code code);
}
