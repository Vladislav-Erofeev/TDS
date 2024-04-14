package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.LayerDetailsDto;
import vlad.erofeev.layerservice.domain.entities.Layer;

@Mapper(uses = {PropsMapper.class, AttributeMapper.class, LayerMapper.class, CodeMapper.class})
public interface LayerDetailsMapper {
    LayerDetailsMapper INSTANCE = Mappers.getMapper(LayerDetailsMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "stringToDate")
    Layer toEntity(LayerDetailsDto attributeDetailsDto);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = "dateToString")
    LayerDetailsDto toDetailsDto(Layer layer);
}
