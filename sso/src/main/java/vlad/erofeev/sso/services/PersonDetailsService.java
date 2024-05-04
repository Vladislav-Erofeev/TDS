package vlad.erofeev.sso.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vlad.erofeev.sso.domain.Person;
import vlad.erofeev.sso.domain.PersonDetails;
import vlad.erofeev.sso.repositories.PersonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByEmail(username);
        if (optionalPerson.isEmpty())
            throw new UsernameNotFoundException(username);
        return new PersonDetails(optionalPerson.get());
    }
}
