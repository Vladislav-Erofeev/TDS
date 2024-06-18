package com.example.projectservice.mappers;

import com.example.projectservice.domain.dtos.ItemDto;
import com.example.projectservice.domain.entities.Line;
import com.example.projectservice.domain.entities.ProjectItem;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.wololo.geojson.Feature;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.HashMap;

@Mapper(uses = {PropsMapper.class})
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Named("geometryToFeature")
    static Feature geometryToFeature(Geometry geometry) {
        return new Feature(new GeoJSONWriter().write(geometry), new HashMap<>());
    }

    @Named("featureToPolygon")
    static Polygon featureToPolygon(Feature feature) {
        if (feature == null)
            return null;
        return (Polygon) new GeoJSONReader().read(feature.getGeometry());
    }

    @Named("featureToLine")
    static LineString featureToLine(Feature feature) {
        if (feature == null)
            return null;
        return (LineString) new GeoJSONReader().read(feature.getGeometry());
    }

    @Mapping(source = "feature", target = "geometry", qualifiedByName = "featureToPolygon")
    @Mapping(source = "codeId", target = "codeId", qualifiedByName = "decodeId")
    @Mapping(source = "personId", target = "personId", qualifiedByName = "decodeId")
    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    com.example.projectservice.domain.entities.Polygon toPolygon(ItemDto itemDto);

    @Mapping(source = "feature", target = "geometry", qualifiedByName = "featureToLine")
    @Mapping(source = "codeId", target = "codeId", qualifiedByName = "decodeId")
    @Mapping(source = "personId", target = "personId", qualifiedByName = "decodeId")
    @Mapping(source = "id", target = "id", qualifiedByName = "decodeId")
    Line toLine(ItemDto itemDto);

    @Mapping(source = "geometry", target = "feature", qualifiedByName = "geometryToFeature")
    @Mapping(source = "codeId", target = "codeId", qualifiedByName = "encodeId")
    @Mapping(source = "personId", target = "personId", qualifiedByName = "encodeId")
    @Mapping(source = "id", target = "id", qualifiedByName = "encodeId")
    ItemDto toDto(ProjectItem projectItem);
}
