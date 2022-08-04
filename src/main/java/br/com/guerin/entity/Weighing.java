package br.com.guerin.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "weighings", schema = "public")
public class Weighing extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name = "id_cattle", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cattle cattle;

    @Getter @Setter
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @Column(name = "weight", nullable = false)
    private Float weight;
}
