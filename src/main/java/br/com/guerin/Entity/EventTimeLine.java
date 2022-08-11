package br.com.guerin.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Gabriel Luiz C
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */

@Entity
@NoArgsConstructor
@Table(name = "event_time_line", schema = "public")
public class EventTimeLine extends  AbstractEntity{
    @Getter @Setter
    @Column(name = "date_event", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @JoinColumn(name = "id_cattleEvent", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //I've added it, cause FetchType.LAZY wasn't working
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CattleEvent cattleEvent;
}
