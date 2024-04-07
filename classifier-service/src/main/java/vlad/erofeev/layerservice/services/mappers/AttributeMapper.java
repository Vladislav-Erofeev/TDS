package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.AttributeDetailsDto;
import vlad.erofeev.layerservice.domain.dto.AttributeDto;
import vlad.erofeev.layerservice.domain.entities.Attribute;

@Mapper(uses = {LayerMapper.class, PropsMapper.class})
public interface AttributeMapper {
    AttributeMapper INSTANCE = Mappers.getMapper(AttributeMapper.class);

    @Mapping(source = "layers", target = "layers",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Attribute toEntity(AttributeDetailsDto attributeDto);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Attribute toEntity(AttributeDto attributeDto);

    @Mapping(source = "layers", target = "layers",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    AttributeDetailsDto toDetailsDto(Attribute attribute);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    AttributeDto toDto(Attribute attribute);
}