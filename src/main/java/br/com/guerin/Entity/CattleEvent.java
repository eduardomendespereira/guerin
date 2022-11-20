package br.com.guerin.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "cattle_events", schema = "public")
public class CattleEvent extends AbstractEntity{

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "cattle_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cattle cattle;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "type_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private EventType eventType;

    @Getter @Setter
    @NotNull
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @Length(min = 3, max = 255, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "description", nullable = true)
    private String description;

    @Getter @Setter
    @JoinColumn(name = "vaccination_id", nullable = true, unique = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private VaccineApplication vaccineApplication;
    @Getter @Setter
    @JoinColumn(name = "weighing_id", nullable = true, unique = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Weighing weighing;

    public CattleEvent(Cattle cattle, EventType eventType, LocalDateTime date, String description, VaccineApplication vaccineApplication) {
        this.cattle = cattle;
        this.eventType = eventType;
        this.date = date;
        this.description = description;
        this.vaccineApplication = vaccineApplication;
    }

    public CattleEvent(Cattle cattle, EventType eventType, LocalDateTime date, String description, Weighing weighing) {
        this.cattle = cattle;
        this.eventType = eventType;
        this.date = date;
        this.description = description;
        this.weighing = weighing;
    }

    public CattleEvent(Cattle cattle, EventType eventType, LocalDateTime date, String description) {
        this.cattle = cattle;
        this.eventType = eventType;
        this.date = date;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CattleEvent that = (CattleEvent) o;
        return Objects.equals(cattle, that.cattle) && Objects.equals(eventType, that.eventType) && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(vaccineApplication, that.vaccineApplication) && Objects.equals(weighing, that.weighing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cattle, eventType, date, description, vaccineApplication, weighing);
    }
}
