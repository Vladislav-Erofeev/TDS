package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeItemDTO {
    private String id;
    private Integer code;
    private String name;
    private String description;
}
