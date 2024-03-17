package vlad.erofeev.sso.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.sso.domain.Person;
import vlad.erofeev.sso.domain.dto.RegistrationRequest;
import vlad.erofeev.sso.domain.Roles;
import vlad.erofeev.sso.repositories.PersonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        Person person = Person.builder()
                .email(registrationRequest.getEmail())
                .name(registrationRequest.getName())
                .surname(registrationRequest.getSurname())
                .role(Roles.USER)
                .password(passwordEncoder.encode(registrationRequest.getPassword())).build();
        personRepository.save(person);
    }

    public Person getById(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty())
            throw new ObjectNotFoundException(id, "Person");
        return optionalPerson.get();
    }
}
