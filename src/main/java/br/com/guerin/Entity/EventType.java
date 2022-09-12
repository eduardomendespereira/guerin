package br.com.guerin.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "event_types", schema = "public")
public class EventType extends AbstractEntity{

    @Getter @Setter
    @NotNull @NotBlank
    @Length(min = 3, max = 50, message = "O nome deverá ter no máximo {max} caracteres")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public EventType(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventType)) return false;
        EventType eventType = (EventType) o;
        return Objects.equals(name, eventType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public EventType(Long id, LocalDateTime registered, boolean inactive, String name) {
        super(id, registered, inactive);
        this.name = name;
    }
}
