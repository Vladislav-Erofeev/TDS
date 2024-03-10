package vlad.erofeev.layerservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String hName;
    private String example;
    private String dataType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_id", referencedColumnName = "id")
    private Layer layer;
}
