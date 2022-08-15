package br.com.guerin.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Table(name = "cattles", schema = "public")
public class Cattle extends AbstractEntity{

    @Getter
    @Setter
    @NotNull @NotBlank
    @Column(name = "earring", nullable = false, unique=true)
    private Long earring;

    @Getter
    @Setter
    @NotNull @NotBlank
    @Column(name = "weight", nullable = false)
    private Float weight;

    @Getter @Setter
    @JoinColumn(name = "specie_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //I've added it, cause FetchType.LAZY wasn't working
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Specie specie;

    @Getter @Setter
    @NotNull
    @JoinColumn(name = "farm_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Farm farm;

    @Getter @Setter
    @NotNull @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Getter @Setter
    @NotNull
    @Column(name = "father", nullable = true)
    private Long father;

    @Getter @Setter
    @NotNull
    @Column(name = "mother", nullable = true)
    private Long mother;

    public Cattle(Long earring) {
        this.earring = earring;
    }

    public Cattle(Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother) {
        this.earring = earring;
        this.weight = weight;
        this.specie = specie;
        this.farm = farm;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
    }

    public Cattle(long earring, float weight, String nelore, String fazenda_generica, Gender male, long father, long mother) {
    }
}