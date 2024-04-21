package vlad.erofeev.layerservice.domain.dto;

import java.util.Date;
import java.util.List;

public record AttributeDetailsDto(String id, String name, String hname, String dataType, String description,
                                  String creationDate, List<LayerDto> layers) {
}
