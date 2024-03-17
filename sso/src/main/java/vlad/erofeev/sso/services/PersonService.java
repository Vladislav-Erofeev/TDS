package vlad.erofeev.sso.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.erofeev.sso.domain.Person;
import vlad.erofeev.sso.domain.RegistrationRequest;
import vlad.erofeev.sso.domain.Roles;
import vlad.erofeev.sso.repositories.PersonRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        Person person = new Person();
        person.setEmail(registrationRequest.getEmail());
        person.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        person.setRole(Roles.USER);
        personRepository.save(person);
    }
}
