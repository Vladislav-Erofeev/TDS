package vlad.erofeev.layerservice.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.layerservice.domain.dto.NewPropertyDTO;
import vlad.erofeev.layerservice.domain.dto.PropertyListItemDTO;
import vlad.erofeev.layerservice.domain.entities.Property;

@Mapper(uses = {LayerMapper.class, PropsMapper.class})
public interface PropertyMapper {
    PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    PropertyListItemDTO convertToPropertyListItemDTO(Property property);

    Property convertToProperty(NewPropertyDTO newPropertyDTO);
}
