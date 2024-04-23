package com.example.geodata.mappers;

import com.example.geodata.domain.dto.NewItemDto;
import com.example.geodata.domain.entities.Line;
import com.example.geodata.domain.entities.Polygon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PropsMapper.class})
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "feature", target = "geometry", qualifiedByName = "featureToPolygon")
    @Mapping(source = "code", target = "codeId", qualifiedByName = "codeDtoToCodeId")
    Polygon toPolygon(NewItemDto newItemDto);

    @Mapping(source = "feature", target = "geometry", qualifiedByName = "featureToLine")
    @Mapping(source = "code", target = "codeId", qualifiedByName = "codeDtoToCodeId")
    Line toLine(NewItemDto newItemDto);
}
