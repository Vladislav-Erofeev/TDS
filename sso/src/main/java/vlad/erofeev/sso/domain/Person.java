package vlad.erofeev.sso.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String name;
    private String surname;

    @Enumerated(value = EnumType.STRING)
    private Roles role;
}
