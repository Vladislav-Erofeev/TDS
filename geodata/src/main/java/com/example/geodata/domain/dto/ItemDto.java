package com.example.geodata.domain.dto;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ItemDto {
    private String id;
    private String creationDate;
    private String codeId;
    private Boolean checked;
    private Map<String, Object> properties;
}
