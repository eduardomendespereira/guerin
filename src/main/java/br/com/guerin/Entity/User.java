package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "users", schema = "public")
public class User extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Getter
    @Setter
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
