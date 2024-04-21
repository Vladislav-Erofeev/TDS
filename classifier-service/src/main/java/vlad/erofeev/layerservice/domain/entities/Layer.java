package vlad.erofeev.layerservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Layer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "h_name", nullable = false, length = Integer.MAX_VALUE)
    private String hname;

    @Column(name = "geometry_type", nullable = false, length = Integer.MAX_VALUE)
    private String geometryType;

    @CreationTimestamp
    private Date creationDate;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "example", length = Integer.MAX_VALUE)
    private String example;

    @Column(name = "icon_url", nullable = false, length = Integer.MAX_VALUE)
    private String iconUrl;

    @ManyToMany
    @JoinTable(name = "attribute_layer",
            joinColumns = @JoinColumn(name = "layer_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id"))
    private List<Attribute> attributes = new LinkedList<>();

    @OneToMany(mappedBy = "layer")
    private List<Code> codes = new LinkedList<>();
}
