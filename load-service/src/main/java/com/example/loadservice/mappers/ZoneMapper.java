package com.example.loadservice.mappers;

import com.example.loadservice.domain.dtos.ZoneDto;
import com.example.loadservice.domain.entities.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PropsMapper.class})
public interface ZoneMapper {
    ZoneMapper INSTANCE = Mappers.getMapper(ZoneMapper.class);

    ZoneDto toDto(Zone zone);

    Zone toEntity(ZoneDto zoneDto);
}
