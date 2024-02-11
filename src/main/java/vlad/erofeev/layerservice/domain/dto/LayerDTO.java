package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LayerDTO {
    private String id;
    private String name;
    private String hName;
    private String description;
    private String iconUrl;
}
