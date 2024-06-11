package com.example.searchservice.services;

import com.example.searchservice.configuration.PathConfig;
import com.example.searchservice.domain.entities.GeocodedFile;
import com.example.searchservice.repositories.GeocodedFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GeocodedFileService {
    private final GeocodedFileRepository geocodedFileRepository;
    private final PathConfig pathConfig;

    @Transactional
    public void save(GeocodedFile geocodedFile) {
        geocodedFileRepository.save(geocodedFile);
    }

    public List<GeocodedFile> getAllByPersonId(Long personId) {
        return geocodedFileRepository.findAllByPersonIdOrderByCreationDateDesc(personId);
    }

    public GeocodedFile getById(Long fileId) {
        Optional<GeocodedFile> optionalGeocodedFile = geocodedFileRepository.findById(fileId);
        return optionalGeocodedFile.orElseThrow(() -> new ObjectNotFoundException(fileId, "GeocodedFile"));
    }

    @Transactional
    public void deleteById(Long fileId, Long personId) throws IllegalAccessException {
        Objects.requireNonNull(personId);

        GeocodedFile geocodedFile = getById(fileId);
        if (!personId.equals(geocodedFile.getPersonId()))
            throw new IllegalAccessException("Access denied");

        // удаление файлов
        try {
            Files.deleteIfExists(Path.of(pathConfig.getPath(), geocodedFile.getReportFile()));
            Files.deleteIfExists(Path.of(pathConfig.getPath(), geocodedFile.getSourceFile()));
            Files.deleteIfExists(Path.of(pathConfig.getPath(), geocodedFile.getCsvReport()));
        } catch (Exception e) {
            log.error("Cannot find file {}", e.getMessage());
        }
        geocodedFileRepository.delete(geocodedFile);
    }
}
