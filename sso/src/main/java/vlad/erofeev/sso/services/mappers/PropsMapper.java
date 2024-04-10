package vlad.erofeev.sso.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface PropsMapper {

    @Named("dateToString")
    static String convertDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(date);
    }

    @Named("stringToDate")
    static Date parseDateFromString(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.parse(date);
    }
}
