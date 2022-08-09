package br.com.guerin.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;


@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "registred", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")  // localdatetime deserializer
    private LocalDateTime registred;

    @Getter
    @Column(name = "updated")
    private LocalDateTime updated;

    @Getter
    @Setter
    @Column(name = "inactive", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean inactive;

    /**
     * @param id
     */
    public AbstractEntity(Long id) {
        this.id = id;
    }

    /**
     * Método executado antes da execução repository.save
     */
    @PrePersist
    public void dateRegistred() {
        this.registred = LocalDateTime.now();
    }

    /**
     * Método executado antes da execução repository.update
     */
    @PreUpdate
    public void dateUpdated() {
        this.updated = LocalDateTime.now();
    }
}
