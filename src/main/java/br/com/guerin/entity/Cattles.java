package br.com.guerin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "gado", schema = "public")
public class Cattles extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "numeracao_brinco", nullable = false)
    private Long brinco;

    @Getter
    @Setter
    @Column(name = "peso", nullable = false)
    private Float peso;

    @Getter @Setter
    @JoinColumn(name = "id_especie", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Specie especie;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sex sex;

    @Getter @Setter
    @Column(name = "pai", nullable = true)
    private Long pai;

    @Getter @Setter
    @Column(name = "mae", nullable = true)
    private Long mae;
}
