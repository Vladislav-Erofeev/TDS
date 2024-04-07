package vlad.erofeev.layerservice.domain.dto;

import java.io.Serializable;
import java.util.Date;

public record LayerDto(Long id, String name, String hName, String geometryType, Date creationDate, String description,
                       String example, String iconUrl) implements Serializable {
}