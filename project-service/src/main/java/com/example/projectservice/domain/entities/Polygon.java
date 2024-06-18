package com.example.projectservice.domain.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("1")
@Getter
@Setter
public class Polygon extends ProjectItem {
    private org.locationtech.jts.geom.Polygon geometry;
}
