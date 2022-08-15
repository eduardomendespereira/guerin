package br.com.guerin.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "weighings", schema = "public")
public class Weighing extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name = "cattle_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //I've added it, cause FetchType.LAZY wasn't working
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cattle cattle;

    @Getter @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @Column(name = "weight", nullable = false)
    private Float weight;
}
