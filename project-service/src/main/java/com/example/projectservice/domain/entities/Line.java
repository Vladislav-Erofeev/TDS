package com.example.projectservice.domain.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.LineString;

@Entity
@Getter
@Setter
@DiscriminatorValue("2")
public class Line extends ProjectItem {
    private LineString geometry;
}
