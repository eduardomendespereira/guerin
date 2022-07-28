package br.com.uniamerica.api.controlegado.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "vacina", schema = "public")
public class Vacina extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 30)
    private String nome;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vacina", nullable = false)
    private TipoVacina status;

    @Getter @Setter
    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Getter @Setter
    @JoinColumn(name = "id_gado", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Gado gado;
}