package vlad.erofeev.layerservice.services.mappers;

import org.hashids.Hashids;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface PropsMapper {
    String SALT = "TESTSALT";

    @Named("encodeId")
    static String encodeId(Long id) {
        return new Hashids(SALT, 4).encode(id);
    }

    @Named("decodeId")
    static long[] decodeId(String id) {
        return new Hashids(SALT, 4).decode(id);
    }
}
