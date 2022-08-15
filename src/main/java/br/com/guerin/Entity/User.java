package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "users", schema = "public")
public class User extends AbstractEntity{
    @Getter
    @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 50, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Getter
    @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 50, message = "O sobrenome deverá ter no máximo {max} caracteres")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Getter
    @Setter
    @Email
    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Getter
    @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 20, message = "O username deverá ter no máximo {max} caracteres")
    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;

    @Getter
    @Setter
    @NotNull @NotBlank
    @Max(255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Getter @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public User(String firstName, String lastName, String email, String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(Long id, LocalDateTime registered, boolean inactive, String firstName, String lastName, String email, String username, String password, Role role) {
        super(id, registered, inactive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
