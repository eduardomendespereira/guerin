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
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JoinColumn(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "required", nullable = false)
    private Boolean required;

    public Vaccine(String name, LocalDateTime date, Boolean required) {
        this.name = name;
        this.date = date;
        this.required = required;
    }

    public boolean dateIsFuture(){
        return date.compareTo(LocalDateTime.now()) > 0;
    }

}