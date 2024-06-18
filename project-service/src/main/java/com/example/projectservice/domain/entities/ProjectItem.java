package com.example.projectservice.domain.entities;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
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
    @Column(updatable = false, nullable = false)
    private Long personId;
    @Column(updatable = false, nullable = false)
    private Long codeId;

    private String name;
    private String addrCountry;
    private String addrCity;
    private String addrStreet;
    private String addrHousenumber;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date creationDate;
    @UpdateTimestamp
    @Column(nullable = false)
    private Date modifiedDate;
    @Type(JsonBinaryType.class)
    private Map<String, Object> properties = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", updatable = false, nullable = false)
    private Project project;

    public abstract Geometry getGeometry();
}
