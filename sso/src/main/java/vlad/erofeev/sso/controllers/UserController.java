package vlad.erofeev.sso.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.sso.domain.dto.ErrorResponse;
import vlad.erofeev.sso.domain.dto.PersonDTO;
import vlad.erofeev.sso.domain.dto.RegistrationRequest;
import vlad.erofeev.sso.exceptions.PersonAlreadyExists;
import vlad.erofeev.sso.services.PersonService;
import vlad.erofeev.sso.services.mappers.PersonMapper;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final PersonService personService;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public PersonDTO register(@RequestBody RegistrationRequest registrationRequest) throws PersonAlreadyExists {
        return personMapper.toDto(personService.register(personMapper.toEntity(registrationRequest)));
    }

    @GetMapping("/profile")
    public PersonDTO getProfile(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return personMapper.toDto(personService.getById(principal.getAttribute("id")));
    }

    @PostMapping("/profile")
    public PersonDTO editProfile(@RequestBody PersonDTO personDTO,
                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return personMapper.toDto(personService.edit(personMapper.toEntity(personDTO),
                Objects.requireNonNull(principal.getAttribute("id"))));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(PersonAlreadyExists exists) {
        System.out.println("exception");
        ErrorResponse errorResponse = new ErrorResponse(exists.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
