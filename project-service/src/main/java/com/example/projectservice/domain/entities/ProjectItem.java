package com.example.projectservice.domain.entities;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when polygon is not null then 1 else 2 end")
public abstract class ProjectItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private Long personId;
    private Long codeId;

    private String name;
    private String addrCountry;
    private String addrCity;
    private String addrStreet;
    private String addrHousenumber;

    private Date creationDate;
    private Date modifiedDate;
    @Type(JsonBinaryType.class)
    private Map<String, Object> properties = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    public abstract Geometry getGeometry();
}
