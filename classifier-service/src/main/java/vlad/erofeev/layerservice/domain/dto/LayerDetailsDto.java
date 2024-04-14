package vlad.erofeev.layerservice.domain.dto;

import java.util.Date;
import java.util.List;

public record LayerDetailsDto(String id, String name, String hname, String geometryType, String creationDate, String description,
                              String example, String iconUrl, List<CodeDto> codes, List<AttributeDto> attributes) {
}
