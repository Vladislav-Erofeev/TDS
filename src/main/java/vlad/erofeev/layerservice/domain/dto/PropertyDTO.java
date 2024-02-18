package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PropertyDTO {
    private String id;
    private String name;
    private String hName;
    private String example;
    private String dataType;
    private LayerItemDTO layer;
}
