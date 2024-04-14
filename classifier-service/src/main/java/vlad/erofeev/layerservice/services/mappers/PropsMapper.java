package vlad.erofeev.layerservice.services.mappers;

import org.hashids.Hashids;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

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
}
