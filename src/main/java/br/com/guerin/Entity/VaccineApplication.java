package br.com.guerin.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "vaccine_applications", schema = "public")
public class VaccineApplication extends AbstractEntity{
    @Getter @Setter
    @NotBlank @NotNull
    @Length(min = 3, max = 100, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "vaccine_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Vaccine vaccine;

    @Getter @Setter
    @Past @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public boolean dateIsFuture(){
        return date.compareTo(LocalDateTime.now()) > 0;
    }
}