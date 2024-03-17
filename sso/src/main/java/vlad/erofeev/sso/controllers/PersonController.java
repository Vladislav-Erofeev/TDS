package vlad.erofeev.sso.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vlad.erofeev.sso.domain.dto.PersonDTO;
import vlad.erofeev.sso.domain.dto.RegistrationRequest;
import vlad.erofeev.sso.services.PersonService;
import vlad.erofeev.sso.services.mappers.PersonMapper;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest registrationRequest) {
        personService.register(registrationRequest);
    }

    @GetMapping("/profile")
    public PersonDTO getProfile(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return personMapper.convertToPersonDTO(personService.getById(principal.getAttribute("id")));
    }
}
