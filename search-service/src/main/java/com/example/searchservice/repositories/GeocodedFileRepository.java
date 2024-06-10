package com.example.searchservice.repositories;

import com.example.searchservice.domain.entities.GeocodedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeocodedFileRepository extends JpaRepository<GeocodedFile, Long> {
    List<GeocodedFile> findAllByPersonIdOrderByCreationDateDesc(Long personId);
}
