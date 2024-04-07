package vlad.erofeev.layerservice.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "attribute")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "h_name", nullable = false, length = Integer.MAX_VALUE)
    private String hname;

    @Column(name = "data_type", nullable = false, length = Integer.MAX_VALUE)
    private String dataType;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @CreationTimestamp
    private Date creationDate;

    @ManyToMany
    @JoinTable(name = "attribute_layer",
            joinColumns = @JoinColumn(name = "attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "layer_id"))
    private List<Layer> layers = new LinkedList<>();

}