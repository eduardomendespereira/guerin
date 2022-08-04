package br.com.guerin.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "cattle_events", schema = "public")
public class CattleEvent extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "id_cattle", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cattle cattle;

    @Getter @Setter
    @JoinColumn(name = "id_type", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EventType eventType;

    @Getter @Setter
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @Column(name = "description", nullable = true)
    private String description;

    @Getter @Setter
    @JoinColumn(name = "id_vaccination", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private VaccineApplication vaccineApplication;
}
