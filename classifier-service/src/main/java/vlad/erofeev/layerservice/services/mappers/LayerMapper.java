package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.AttributeDetailsDto;
import vlad.erofeev.layerservice.domain.dto.LayerDetailsDto;
import vlad.erofeev.layerservice.domain.dto.LayerDto;
import vlad.erofeev.layerservice.domain.entities.Attribute;
import vlad.erofeev.layerservice.domain.entities.Layer;

@Mapper(uses = {PropsMapper.class})
public interface LayerMapper {
    LayerMapper INSTANCE = Mappers.getMapper(LayerMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    LayerDto toDto(Layer layer);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Layer toEntity(LayerDto layerDto);
}
