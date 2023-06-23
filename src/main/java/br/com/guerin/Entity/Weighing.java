package br.com.guerin.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "weighings", schema = "public")
public class Weighing extends AbstractEntity {

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "cattle_id", nullable = false, unique = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Cattle cattle;

    @Getter @Setter
    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm:ss")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weighing weighing = (Weighing) o;
        return Objects.equals(cattle, weighing.cattle) && Objects.equals(date, weighing.date) && Objects.equals(weight, weighing.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cattle, date, weight);
    }
}
