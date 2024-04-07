package vlad.erofeev.layerservice.domain.dto;

import java.io.Serializable;
import java.util.Date;

public record LayerDto(String id, String name, String hname, String geometryType, Date creationDate, String description,
                       String example, String iconUrl) implements Serializable {
}