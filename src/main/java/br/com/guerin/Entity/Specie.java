package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "species", schema = "public")
public class Specie extends AbstractEntity{
    @Getter
    @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 50, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specie)) return false;
        Specie specie = (Specie) o;
        return Objects.equals(name, specie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    public Specie(String name) {
        this.name = name;
    }
}
