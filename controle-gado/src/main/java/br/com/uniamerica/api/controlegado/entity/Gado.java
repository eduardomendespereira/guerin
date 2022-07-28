package br.com.uniamerica.api.controlegado.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "gado", schema = "public")
public class Gado extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 30)
    private String nome;
}
