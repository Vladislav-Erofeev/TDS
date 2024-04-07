package vlad.erofeev.layerservice.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link vlad.erofeev.layerservice.domain.entities.Code}
 */
public record CodeDto(String id, LayerDto layer, Integer code, String name, String description,
                      Date creationDate) implements Serializable {
}