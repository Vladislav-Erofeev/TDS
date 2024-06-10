package com.example.searchservice.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "geocoded_file")
public class GeocodedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long personId;
    private Date creationDate;
    private String sourceFile;
    private String reportFile;
    private String csvReport;
    private Long total;
    private Long found;

    @Enumerated(EnumType.STRING)
    private GeocodedFileStatus status;
}
