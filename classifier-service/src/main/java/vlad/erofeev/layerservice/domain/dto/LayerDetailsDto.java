package vlad.erofeev.layerservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LayerDetailsDto {
    private String id;
    private String name;
    private String hname;
    private String geometryType;
    private String creationDate;
    private String description;
    private String example;
    private List<CodeDto> codes = new LinkedList<>();
    private List<AttributeDto> attributes = new LinkedList<>();
}
