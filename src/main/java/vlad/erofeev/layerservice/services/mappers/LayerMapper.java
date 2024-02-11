package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.LayerDTO;
import vlad.erofeev.layerservice.domain.entities.Layer;

@Mapper(uses = {PropsMapper.class})
public interface LayerMapper {
    LayerMapper INSTANCE = Mappers.getMapper(LayerMapper.class);

    @Named("convertId")
    static Long convertId(String id) {
        if (id == null)
            return null;
        return PropsMapper.decodeId(id)[0];
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    LayerDTO convertToLayerDTO(Layer layer);

    @Mapping(source = "id", target = "id", qualifiedByName = "convertId")
    Layer convertToLayer(LayerDTO layerDTO);
}
