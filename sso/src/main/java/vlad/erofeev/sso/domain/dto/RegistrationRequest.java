package vlad.erofeev.sso.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
    private Integer phone;
    private String addr;
    private String name;
    private String surname;
    private String lastname;
    private String birthDate;
}
