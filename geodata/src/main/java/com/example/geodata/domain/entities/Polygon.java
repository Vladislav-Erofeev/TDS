package com.example.geodata.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@DiscriminatorValue("1")
public class Polygon extends Item {
    @Column(name = "polygon")
    private org.locationtech.jts.geom.Polygon geometry;
}
