package vlad.erofeev.layerservice.domain.dto;

import java.util.Date;

public record CodeListItemDto(String id, Integer code, String name, String description,
                              Date creationDate) {
}
