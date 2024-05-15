package vlad.erofeev.sso.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.management.relation.Role;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private Long phone;
    private String addr;
    private String name;
    private String surname;
    private String lastname;
    private Date birthDate;

    @CreationTimestamp
    private Date registrationDate;


    @Enumerated(value = EnumType.STRING)
    private Roles role;
}
