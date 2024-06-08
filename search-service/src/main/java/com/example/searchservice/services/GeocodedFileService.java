package com.example.searchservice.services;

import com.example.searchservice.entities.GeocodedFile;
import com.example.searchservice.repositories.GeocodedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeocodedFileService {
    private final GeocodedFileRepository geocodedFileRepository;

    @Transactional
    public void save(GeocodedFile geocodedFile) {
        geocodedFileRepository.save(geocodedFile);
    }

    public List<GeocodedFile> getAllByPersonId(Long personId) {
        return geocodedFileRepository.findAllByPersonId(personId);
    }
}
