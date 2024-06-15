package vlad.erofeev.sso.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import vlad.erofeev.sso.domain.Roles;

import java.util.Date;

@Getter
@Setter
public class PersonDTO {
    private String id;
    private String email;
    private String password;
    private Long phone;
    private String addr;
    private String name;
    private String surname;
    private String lastname;
    private String birthDate;
    private String registrationDate;
    private Roles role;
}
