package com.example.geodata.domain.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when polygon is not null then 1 else 2 end")
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date creationDate;
    private Long codeId;
    private Boolean checked;

    @Type(JsonBinaryType.class)
    private Map<String, Object> properties = new HashMap<>();

    public abstract Geometry getGeometry();
}
