package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewLayerDTO {
    private String name;
    private String hName;
    private String description;
    private String iconUrl;
}
