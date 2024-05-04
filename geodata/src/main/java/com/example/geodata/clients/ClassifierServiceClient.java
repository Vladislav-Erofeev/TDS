package com.example.geodata.clients;

import com.example.geodata.domain.dto.CodeDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "classifier")
public interface ClassifierServiceClient {
    @RequestMapping(method = RequestMethod.GET, path = "/codes/{id}")
    @Cacheable(cacheNames = "values", key = "#id", unless = "#result == null")
    CodeDto getCodeById(@PathVariable("id") String id);
}
