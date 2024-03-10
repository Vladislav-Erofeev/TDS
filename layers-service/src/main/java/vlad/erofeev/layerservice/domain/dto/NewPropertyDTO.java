package vlad.erofeev.layerservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewPropertyDTO {
    private String name;
    private String hName;
    private String example;
    private String dataType;
    private String layerId;
}
