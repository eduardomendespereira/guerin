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
    @NotNull
    @Length(min = 3, max = 255, message = "A descrição deverá ter no máximo {max} caracteres")
    @Column(name = "note", nullable = false)
    private String note;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "vaccine_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Vaccine vaccine;

    @Getter @Setter
    @Past @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "cattle_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cattle cattle;

    public boolean dateIsFuture(){
        return date.compareTo(LocalDateTime.now()) > 0;
    }

    public VaccineApplication(String note, Vaccine vaccine, LocalDateTime date, Cattle cattle) {
        this.note = note;
        this.vaccine = vaccine;
        this.date = date;
        this.cattle = cattle;
    }
}