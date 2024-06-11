package com.example.loadservice.services;

import com.example.loadservice.domain.entities.Zone;
import com.example.loadservice.repositories.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        if (!zone.getPersonId().equals(personId))
            throw new IllegalAccessException("Access denied");
        return zone;
    }

    @Transactional
    public Zone save(Zone zone, Long personId) {
        zone.setPersonId(personId);
        zone.setCreationDate(new Date());
        return zoneRepository.save(zone);
    }

    @Transactional
    public void deleteById(Long zoneId, Long personId) throws IllegalAccessException {
        Zone zone = getById(zoneId, personId);
        zoneRepository.delete(zone);
    }

    @Transactional
    public void editById(Long zoneId, Long personId, String name) throws IllegalAccessException {
        Zone oldZone = getById(zoneId, personId);
        oldZone.setName(name);
        zoneRepository.save(oldZone);
    }
}
