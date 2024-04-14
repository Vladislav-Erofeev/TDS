package vlad.erofeev.layerservice.domain.dto;

import java.util.Date;

public record CodeDto(String id, Integer code, String name, String description,
                      String creationDate) {
}
