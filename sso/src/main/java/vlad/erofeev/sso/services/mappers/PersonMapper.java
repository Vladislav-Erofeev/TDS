package vlad.erofeev.sso.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.sso.domain.Person;
import vlad.erofeev.sso.domain.dto.PersonDTO;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO convertToPersonDTO(Person person);
}
