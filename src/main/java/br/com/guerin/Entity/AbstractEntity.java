package br.com.guerin.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "registered", nullable = true)
    @Past
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  // localdatetime deserializer
    private LocalDateTime registered;

    @Getter
    @Past
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  // localdatetime deserializer
    @Column(name = "updated")
    private LocalDateTime updated;

    @Getter
    @Setter
    @Column(name = "inactive", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean inactive;

    /**
     * @param id
     */

    public AbstractEntity(Long id, LocalDateTime registered, boolean inactive) {
        this.id = id;
        this.registered = registered;
        this.inactive = inactive;
    }
    /**
     * Método executado antes da execução repository.save
     */
    @PrePersist
    public void dateRegistered() {
        this.registered = LocalDateTime.now();
    }

    /**
     * Método executado antes da execução repository.update
     */
    @PreUpdate
    public void dateUpdated() {
        this.updated = LocalDateTime.now();
    }


}
