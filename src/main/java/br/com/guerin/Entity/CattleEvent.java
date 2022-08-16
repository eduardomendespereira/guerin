package br.com.guerin.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "cattle_events", schema = "public")
public class CattleEvent extends AbstractEntity{

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "cattle_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cattle cattle;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "type_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EventType eventType;

    @Getter @Setter
    @Past @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @Length(min = 3, max = 255, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "description", nullable = true)
    private String description;

    @Getter @Setter
    @JoinColumn(name = "vaccination_id", nullable = true, unique = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private VaccineApplication vaccineApplication;

    @Getter @Setter
    @JoinColumn(name = "weighing_id", nullable = true, unique = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Weighing weighing;
}
