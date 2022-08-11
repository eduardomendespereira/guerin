package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "vaccines", schema = "public")
public class Vaccine extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Getter @Setter
    @JoinColumn(name = "date", nullable = true)
    private LocalDateTime date;

    @Getter @Setter
    @JoinColumn(name = "required", nullable = true)
    private Boolean required;

    public boolean dateIsFuture(){
        return date.compareTo(LocalDateTime.now()) > 0;
    }

}
