package br.com.guerin.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "cattles", schema = "public")
public class Cattle extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "earring", nullable = false)
    private Long earring;

    @Getter
    @Setter
    @Column(name = "weight", nullable = false)
    private Float weight;

    @Getter @Setter
    @JoinColumn(name = "specie_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Specie specie;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Getter @Setter
    @Column(name = "father", nullable = true)
    private Long father;

    @Getter @Setter
    @Column(name = "mother", nullable = true)
    private Long mother;
}
