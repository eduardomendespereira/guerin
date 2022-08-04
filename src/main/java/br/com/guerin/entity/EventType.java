package br.com.guerin.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "event_types", schema = "public")
public class EventType extends AbstractEntity{

    @Getter @Setter
    @Column(name = "name", nullable = false)
    private String name;
}
