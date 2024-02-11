package vlad.erofeev.layerservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_id", referencedColumnName = "id")
    private Layer layer;
}
