package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.LayerDTO;
import vlad.erofeev.layerservice.domain.entities.Layer;

@Mapper
public interface LayerMapper {
    LayerMapper INSTANCE = Mappers.getMapper(LayerMapper.class);

    LayerDTO convertToLayerDTO(Layer layer);
}
