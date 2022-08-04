package br.com.guerin.entity;

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
    @Column(name = "name", nullable = false)
    private String name;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "vaccine_type", nullable = false)
    private VaccineType vaccineType;

    @Getter @Setter
    @JoinColumn(name = "date", nullable = true)
    private LocalDateTime date;

    @Getter @Setter
    @JoinColumn(name = "inactive_date", nullable = true)
    private LocalDateTime dateInac;

    @Getter @Setter
    @JoinColumn(name = "required", nullable = true)
    private Boolean required;
}
