package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyListItemDTO {
    private String id;
    private String name;
    private String hName;
    private String example;
    private String dataType;
}
