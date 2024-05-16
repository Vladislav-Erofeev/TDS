package vlad.erofeev.layerservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class LayerDto {
    private String id;
    private String name;
    private String hname;
    private String geometryType;
    private String creationDate;
    private String description;
    private String example;
}