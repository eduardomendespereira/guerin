package br.com.guerin.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "weighings", schema = "public")
public class Weighing extends AbstractEntity {

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "cattle_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //I've added it, cause FetchType.LAZY wasn't working
    @OneToOne(fetch = FetchType.LAZY)
    private Cattle cattle;

    @Getter @Setter
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter @Setter
    @NotNull
    @Column(name = "weight", nullable = false)
    private Float weight;

    public Weighing(Cattle cattle, LocalDateTime date, Float weight) {
        this.cattle = cattle;
        this.date = date;
        this.weight = weight;
    }
}
