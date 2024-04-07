package vlad.erofeev.layerservice.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

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

}
