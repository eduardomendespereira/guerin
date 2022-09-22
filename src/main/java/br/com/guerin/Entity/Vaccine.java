package br.com.guerin.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "vaccines", schema = "public")
public class Vaccine extends AbstractEntity{
    @Getter @Setter
    @NotNull
    @Length(min = 3, max = 50, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "required", nullable = false)
    private Boolean required;

    public Vaccine(String name, LocalDateTime date, Boolean required) {
        this.name = name;
        this.required = required;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaccine vaccine = (Vaccine) o;
        return Objects.equals(name, vaccine.name) && Objects.equals(required, vaccine.required);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, required);
    }
}