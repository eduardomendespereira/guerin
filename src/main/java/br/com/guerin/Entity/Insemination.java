package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "inseminations", schema = "public")
public class Insemination extends AbstractEntity{

    @Getter @Setter
    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Getter
    @Setter
    @NotNull
    @JoinColumn(name = "cattle_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Cattle cattle;

    public Insemination(LocalDateTime date, Cattle cattle) {
        this.date = date;
        this.cattle = cattle;
    }

    public Insemination() {
    }
}
