package vlad.erofeev.sso.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vlad.erofeev.sso.domain.Person;
import vlad.erofeev.sso.domain.dto.PersonDTO;

@Mapper(uses = {PropsMapper.class})
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "dateToString")
    @Mapping(source = "registrationDate", target = "registrationDate", qualifiedByName = "dateToString")
    PersonDTO toDto(Person person);

    @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "stringToDate")
    @Mapping(source = "registrationDate", target = "registrationDate", qualifiedByName = "stringToDate")
    Person toEntity(PersonDTO personDTO);
}
