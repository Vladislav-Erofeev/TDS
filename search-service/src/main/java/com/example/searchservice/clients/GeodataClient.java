package com.example.searchservice.clients;

import com.example.searchservice.domain.dtos.CentroidDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geodata")
public interface GeodataClient {
    @GetMapping("/objects/centroid")
    CentroidDto getCentroid(@RequestParam("id") String id);
}
