package vlad.erofeev.layerservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
public class Layer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String hName;
    private String description;
    private String iconUrl;

    @OneToMany(mappedBy = "layer")
    private List<Code> codes;
}
