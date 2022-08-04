package br.com.guerin.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "vaccine_applications", schema = "public")
public class VaccineApplication extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @JoinColumn(name = "id_vaccine", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Vaccine vaccine;

    @Getter
    @Setter
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
