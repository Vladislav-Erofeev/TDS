package vlad.erofeev.sso.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {
    private String email;
    private String role;
    private String name;
    private String surname;
}
