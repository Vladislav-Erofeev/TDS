package vlad.erofeev.layerservice.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link vlad.erofeev.layerservice.domain.entities.Attribute}
 */
public record AttributeDto(String id, String name, String hname, String dataType, String description,
                           String creationDate) implements Serializable {
}