package br.com.guerin.Entity;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "event_types", schema = "public")
public class EventType extends AbstractEntity{

    @Getter @Setter
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
}
