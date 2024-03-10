package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vlad.erofeev.layerservice.domain.entities.Code;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LayerDTO {
    private String id;
    private String name;
    private String hName;
    private String description;
    private String iconUrl;
    private List<CodeItemDTO> codes;
    private List<PropertyListItemDTO> properties;
}
