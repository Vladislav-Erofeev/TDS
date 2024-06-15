package vlad.erofeev.sso.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vlad.erofeev.sso.domain.Roles;
import vlad.erofeev.sso.domain.dto.PersonDTO;
import vlad.erofeev.sso.services.PersonService;
import vlad.erofeev.sso.services.mappers.PersonMapper;
import vlad.erofeev.sso.services.mappers.PropsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/persons")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequiredArgsConstructor
@RestController
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @GetMapping
    public List<PersonDTO> getALl() {
        return personService.getALl().stream().map(personMapper::toDto).collect(Collectors.toList());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public PersonDTO getById(@PathVariable("id") String id) {
        return personMapper.toDto(personService.getById(PropsMapper.decodeId(id)));
    }

    @PostMapping("/{id}/role")
    public PersonDTO setRoleToPerson(@PathVariable("id") int id,
                                     @RequestParam("role")Roles role) {
        return personMapper.toDto(personService.setPersonRole(id, role));
    }
}
