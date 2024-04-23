package com.example.geodata.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import org.locationtech.jts.geom.LineString;

@Entity
@Data
@DiscriminatorValue("2")
public class Line extends Item{
    @Column(name = "line")
    private LineString geometry;
}
