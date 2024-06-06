package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class AttributeDetailsDto {
    private String id;
    private String name;
    private String hname;
    private String dataType;
    private Boolean required;
    private String description;
    private String creationDate;
    private List<LayerDto> layers = new LinkedList<>();
}
