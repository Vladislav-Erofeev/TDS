package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeDto {
    private String id;
    private String name;
    private String hname;
    private String dataType;
    private String description;
    private String creationDate;
}