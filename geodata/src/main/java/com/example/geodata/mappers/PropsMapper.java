package com.example.geodata.mappers;

import com.example.geodata.domain.dto.CodeDto;
import com.example.geodata.domain.entities.Line;
import org.hashids.Hashids;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.wololo.geojson.Feature;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Named("dateToString")
    static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return date == null ? null : simpleDateFormat.format(date);
    }

    @Named("stringToDate")
    static Date parseDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return date == null ? null : simpleDateFormat.parse(date);
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

    @Named("codeDtoToCodeId")
    static Long codeDtoToCodeId(CodeDto codeDto) {
        return codeDto == null ? null : decodeId(codeDto.id());
    }
}
