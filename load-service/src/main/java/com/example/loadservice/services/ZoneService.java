package com.example.loadservice.services;

import com.example.loadservice.domain.entities.Zone;
import com.example.loadservice.exceptions.IllegalGeometryException;
import com.example.loadservice.repositories.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.geojson.Feature;
import org.wololo.jts2geojson.GeoJSONReader;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ZoneService {
    private final ZoneRepository zoneRepository;

    public Zone getById(Long zoneId, Long personId) throws IllegalAccessException {
        Optional<Zone> zoneOptional = zoneRepository.findById(zoneId);
        Zone zone = zoneOptional.orElseThrow(() -> new ObjectNotFoundException("Zone", zoneId));

        // проверка прав пользователя на доступ к зоне
        if (!zone.getPersonId().equals(zoneId))
            throw new IllegalAccessException("Access denied");
        return zone;
    }

    @Transactional
    public Zone save(Zone zone, Feature feature, Long personId) throws IllegalGeometryException {
        Objects.requireNonNull(feature);
        // проверка типа геометрии
        if (!feature.getGeometry().getType().equals("Polygon"))
            throw new IllegalGeometryException("Geometry type must be Polygon");

        Polygon polygon = (Polygon) new GeoJSONReader().read(feature.getGeometry());
        // проверка топологии геометрии
        if (!polygon.isValid())
            throw new IllegalArgumentException("Geometry should be valid");
        zone.setGeometry(polygon);
        zone.setPersonId(personId);
        zone.setCreationDate(new Date());
        return zoneRepository.save(zone);
    }
}
