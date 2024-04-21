package vlad.erofeev.sso.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.sso.domain.Person;
import vlad.erofeev.sso.domain.dto.RegistrationRequest;
import vlad.erofeev.sso.domain.Roles;
import vlad.erofeev.sso.exceptions.PersonAlreadyExists;
import vlad.erofeev.sso.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) throws PersonAlreadyExists {
        Optional<Person> optionalPerson = personRepository.findByEmail(person.getEmail());
        if (optionalPerson.isPresent())
            throw new PersonAlreadyExists(person.getEmail());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Roles.USER);
        personRepository.save(person);
    }

    @Transactional
    public Person edit(Person person, long id) {
        Person oldPerson = getById(id);
        person.setId(id);
        person.setPassword(oldPerson.getPassword());
        person.setEmail(oldPerson.getEmail());
        person.setRegistrationDate(oldPerson.getRegistrationDate());
        person.setRole(oldPerson.getRole());
        return personRepository.save(person);
    }

    public Person getById(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty())
            throw new ObjectNotFoundException(id, "Person");
        return optionalPerson.get();
    }

    public List<Person> getALl() {
        return personRepository.findAll();
    }

    @Transactional
    public Person setPersonRole(long id, Roles role) {
        Person person = getById(id);
        person.setRole(role);
        return personRepository.save(person);
    }
}
