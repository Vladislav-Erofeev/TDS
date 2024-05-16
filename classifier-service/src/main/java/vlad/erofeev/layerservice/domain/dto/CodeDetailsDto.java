package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class CodeDetailsDto {
    private String id;
    private LayerDto layer;
    private Integer code;
    private String name;
    private String description;
    private String creationDate;
}