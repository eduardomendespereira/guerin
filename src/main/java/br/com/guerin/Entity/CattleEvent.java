package br.com.guerin.Entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "cattle_events", schema = "public")
public class CattleEvent extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "cattle_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cattle cattle;

    @Getter @Setter
    @JoinColumn(name = "type_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EventType eventType;

    @Getter @Setter
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @Column(name = "description", nullable = true)
    private String description;

    @Getter @Setter
    @JoinColumn(name = "vaccination_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private VaccineApplication vaccineApplication;
}
