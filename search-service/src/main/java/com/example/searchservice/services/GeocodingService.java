package com.example.searchservice.services;

import com.example.searchservice.entities.GeocodedFile;
import com.example.searchservice.entities.GeocodedFileStatus;
import com.example.searchservice.entities.GeocodingItem;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GeocodingService {
    private final TemplateEngine templateEngine;
    private final GeocodedFileService geocodedFileService;
    private final EsService esService;
    @Value("${workdir.path}")
    private String WORKDIR_PATH;

    @SneakyThrows
    @PostConstruct
    public void init() {
        if (Files.notExists(Path.of(WORKDIR_PATH, "geocoding")))
            Files.createDirectory(Path.of(WORKDIR_PATH, "geocoding"));
        if (Files.notExists(Path.of(WORKDIR_PATH, "geocoding", "source")))
            Files.createDirectory(Path.of(WORKDIR_PATH, "geocoding", "source"));
        if (Files.notExists(Path.of(WORKDIR_PATH, "geocoding", "report")))
            Files.createDirectory(Path.of(WORKDIR_PATH, "geocoding", "report"));
    }

    public void saveFile(MultipartFile file, Long personId) throws IOException {
        GeocodedFile geocodedFile = GeocodedFile.builder()
                .creationDate(new Date())
                .personId(personId)
                .status(GeocodedFileStatus.CREATED).build();
        geocodedFileService.save(geocodedFile);

        String fileName = UUID.randomUUID().toString();
        try (OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(Path.of(WORKDIR_PATH, "geocoding", "source", fileName).toFile()))) {
            file.getInputStream().transferTo(outputStream);
        }

        geocodedFile.setSourceFile(Path.of("geocoding", "source", fileName).toString());
        geocodedFile.setStatus(GeocodedFileStatus.STARTED);
        geocodedFileService.save(geocodedFile);

        processAddresses(Path.of(WORKDIR_PATH, "geocoding", "source", fileName), fileName);

        geocodedFile.setStatus(GeocodedFileStatus.DONE);
        geocodedFile.setReportFile(Path.of("geocoding", "report", fileName + ".html").toString());
        geocodedFileService.save(geocodedFile);
    }

    @Async
    protected void processAddresses(Path file, String fileName) throws IOException {
        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file.toFile())));
        List<GeocodingItem> addresses = new LinkedList<>();
        while (scanner.hasNextLine()) {
            String address = scanner.nextLine();
            GeocodingItem geocodingItem = new GeocodingItem();
            geocodingItem.setRequest(address);
            geocodingItem.setItem(esService.findBestMatch(address).orElse(null));
            addresses.add(geocodingItem);
        }
        generateStaticHtmlContent(addresses, fileName);
    }

    private void generateStaticHtmlContent(List<GeocodingItem> addresses, String fileName) {
        try (PrintWriter printWriter = new PrintWriter(Path.of(WORKDIR_PATH, "geocoding", "report", fileName + ".html").toFile())) {
            Context context = new Context();
            context.setVariable("list", addresses);
            printWriter.write(templateEngine.process("addresses.html", context));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
