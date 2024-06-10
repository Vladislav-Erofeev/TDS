package com.example.searchservice.services;

import com.example.searchservice.configuration.PathConfig;
import com.example.searchservice.domain.dtos.ItemDto;
import com.example.searchservice.domain.entities.GeocodedFile;
import com.example.searchservice.domain.entities.GeocodedFileStatus;
import com.example.searchservice.domain.entities.GeocodingItem;
import com.example.searchservice.domain.entities.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class GeocodingService {
    private final TemplateEngine templateEngine;
    private final GeocodedFileService geocodedFileService;
    private final EsService esService;
    private final NotificationService notificationService;
    private final Hashids hashids = new Hashids("TESTSALT", 4);
    private final PathConfig pathConfig;
    private final Executor executor = Executors.newFixedThreadPool(5);

    @SneakyThrows
    @PostConstruct
    public void init() {
        if (Files.notExists(Path.of(pathConfig.getPath(), pathConfig.getGeocodingSource())))
            Files.createDirectory(Path.of(pathConfig.getPath(), pathConfig.getGeocodingSource()));
        if (Files.notExists(Path.of(pathConfig.getPath(), pathConfig.getGeocodingReport())))
            Files.createDirectory(Path.of(pathConfig.getPath(), pathConfig.getGeocodingReport()));
    }

    public void saveFile(MultipartFile file, Long personId) throws IOException {
        GeocodedFile geocodedFile = GeocodedFile.builder()
                .creationDate(new Date())
                .personId(personId)
                .status(GeocodedFileStatus.CREATED).build();
        geocodedFileService.save(geocodedFile);

        String fileName = UUID.randomUUID().toString();
        try (OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(Path.of(pathConfig.getPath(), pathConfig.getGeocodingSource(), fileName).toFile()))) {
            file.getInputStream().transferTo(outputStream);
        }

        geocodedFile.setSourceFile(Path.of(pathConfig.getGeocodingSource(), fileName).toString());
        geocodedFile.setStatus(GeocodedFileStatus.STARTED);
        geocodedFileService.save(geocodedFile);
        notificationService.sendMessage(geocodedFile.getPersonId(), geocodedFile);

        executor.execute(() -> {
            try {
                processAddresses(Path.of(pathConfig.getPath(), pathConfig.getGeocodingSource(), fileName), fileName, geocodedFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void processAddresses(Path file, String fileName, GeocodedFile geocodedFile) throws IOException {
        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file.toFile())));
        List<GeocodingItem> addresses = new LinkedList<>();
        try (PrintWriter printWriter = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(
                                Path.of(pathConfig.getPath(),
                                        pathConfig.getGeocodingReport(), fileName + ".csv").toFile())))) {
            printWriter.println("address,x,y");
            while (scanner.hasNextLine()) {
                String address = scanner.nextLine();
                GeocodingItem geocodingItem = new GeocodingItem();
                geocodingItem.setRequest(address);
                geocodingItem.setItem(toDto(esService.findBestMatch(address).orElse(null)));
                addresses.add(geocodingItem);
                if (geocodingItem.getItem() == null) {
                    printWriter.printf(Locale.US, "%s,%f,%f\n", address, 0f, 0f);
                } else {
                    printWriter.printf(Locale.US, "%s,%f,%f\n", address, geocodingItem.getItem().getCentroid().getX(),
                            geocodingItem.getItem().getCentroid().getY());
                }
            }
        }
        geocodedFile.setTotal(Long.valueOf(addresses.size()));
        geocodedFile.setFound(addresses.stream().filter(item -> item.getItem() != null).count());
        generateStaticHtmlContent(addresses, fileName);

        geocodedFile.setStatus(GeocodedFileStatus.DONE);
        geocodedFile.setReportFile(Path.of(pathConfig.getGeocodingReport(), fileName + ".html").toString());
        geocodedFile.setCsvReport(Path.of(pathConfig.getGeocodingReport(), fileName + ".csv").toString());
        geocodedFileService.save(geocodedFile);
        notificationService.sendMessage(geocodedFile.getPersonId(), geocodedFile);
    }

    private void generateStaticHtmlContent(List<GeocodingItem> addresses, String fileName) {
        try (PrintWriter printWriter = new PrintWriter(Path.of(pathConfig.getPath(), pathConfig.getGeocodingReport(), fileName + ".html").toFile())) {
            Context context = new Context();
            context.setVariable("list", addresses);
            printWriter.write(templateEngine.process("addresses.html", context));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ItemDto toDto(Item item) {
        if (item == null)
            return null;
        ItemDto itemDto = new ItemDto();
        itemDto.setId(hashids.encode(item.getId()));
        itemDto.setName(item.getName());
        itemDto.setAddr_city(item.getAddr_city());
        itemDto.setAddr_country(item.getAddr_country());
        itemDto.setAddr_street(item.getAddr_street());
        itemDto.setAddr_housenumber(item.getAddr_housenumber());
        itemDto.setCentroid(item.getCentroid());
        return itemDto;
    }
}
