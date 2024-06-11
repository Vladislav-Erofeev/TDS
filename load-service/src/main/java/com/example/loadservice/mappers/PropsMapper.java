package com.example.loadservice.mappers;

import com.example.loadservice.exceptions.IllegalGeometryException;
import org.hashids.Hashids;
import org.locationtech.jts.geom.Polygon;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.wololo.geojson.Feature;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.HashMap;

@Mapper
public interface PropsMapper {
    String SALT = "TESTSALT";

    @Named("encodeId")
    static String encodeId(Long id) {
        return new Hashids(SALT, 4).encode(id);
    }

    @Named("convertId")
    static long[] convertId(String id) {
        return new Hashids(SALT, 4).decode(id);
    }

    @Named("decodeId")
    static Long decodeId(String id) throws IllegalArgumentException {
        if (id == null)
            return null;
        long[] decoded = PropsMapper.convertId(id);
        if (decoded.length == 0)
            throw new IllegalArgumentException(String.format("Illegal id format id=%s", id));
        return decoded[0];
    }

    @Named("featureToGeometry")
    static Polygon featureToPolygon(Feature feature) throws IllegalGeometryException {
        if (!feature.getGeometry().getType().equals("Polygon"))
            throw new IllegalGeometryException("Geometry type must be Polygon");

        Polygon polygon = (Polygon) new GeoJSONReader().read(feature.getGeometry());
        // проверка топологии геометрии
        if (!polygon.isValid())
            throw new IllegalArgumentException("Geometry should be valid");
        return (Polygon) new GeoJSONReader().read(feature.getGeometry());
    }

    @Named("polygonToFeature")
    static Feature polygonToFeature(Polygon polygon) {
        return new Feature(new GeoJSONWriter().write(polygon), new HashMap<>());
    }
}
