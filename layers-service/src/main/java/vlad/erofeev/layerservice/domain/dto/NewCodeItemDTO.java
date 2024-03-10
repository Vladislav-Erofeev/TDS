package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewCodeItemDTO {
    private String layerId;
    private Integer code;
    private String name;
    private String description;
}
