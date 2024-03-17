package vlad.erofeev.sso.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vlad.erofeev.sso.domain.RegistrationRequest;
import vlad.erofeev.sso.services.PersonService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
public class PersonController {
    private final PersonService personService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest registrationRequest) {
        personService.register(registrationRequest);
    }

    @GetMapping("/saved")
    public String get() {
        return "access!";
    }
}
