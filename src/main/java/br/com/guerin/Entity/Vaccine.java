package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "vaccines", schema = "public")
public class Vaccine extends AbstractEntity{
    @Getter @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 25, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Getter @Setter
    @NotNull @Past
    @JoinColumn(name = "date", nullable = true)
    private LocalDateTime date;

    @Getter @Setter
    @JoinColumn(name = "required", nullable = true)
    private Boolean required;

    public boolean dateIsFuture(){
        return date.compareTo(LocalDateTime.now()) > 0;
    }

}