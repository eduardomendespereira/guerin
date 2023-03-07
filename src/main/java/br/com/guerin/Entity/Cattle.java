package br.com.guerin.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "cattles", schema = "public")
public class Cattle extends AbstractEntity{

    @Getter
    @Setter
    @NotNull
    @Column(name = "earring", nullable = false, unique=true)
    private Long earring;

    @Getter
    @Setter
    @Column(name = "weight", nullable = false)
    private Float weight;

    @Getter @Setter
    @JoinColumn(name = "specie_id", nullable = true)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Specie specie;

    @Getter @Setter
    @JoinColumn(name = "farm_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Farm farm;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CattleStatus status;

    @Getter @Setter
    @Column(name = "father", nullable = true)
    private Long father;

    @Getter @Setter
    @Column(name = "mother", nullable = true)
    private Long mother;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "bornAt", nullable = false)
    private LocalDate bornAt;

    @Getter @Setter
    @Column(name = "breastFeeding", nullable = false)
    private Boolean breastFeeding;

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

    public Cattle(
            long earring,
            float weight,
            String nelore,
            String fazenda_generica,
            Gender male,
            long father,
            long mother
    ) {

    }

    public Cattle(Long earring, Float weight, Specie specie, Farm farm, Gender gender, Long father, Long mother,
                  LocalDate bornAt, Boolean breastFeeding, CattleStatus status)
    {
        this.earring = earring;
        this.weight = weight;
        this.specie = specie;
        this.farm = farm;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.bornAt = bornAt;
        this.breastFeeding = breastFeeding;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cattle cattle = (Cattle) o;
        return Objects.equals(earring, cattle.earring) && Objects.equals(weight, cattle.weight) && Objects.equals(specie, cattle.specie) && Objects.equals(farm, cattle.farm) && gender == cattle.gender && Objects.equals(father, cattle.father) && Objects.equals(mother, cattle.mother);
    }

    @Override
    public int hashCode() {
        return Objects.hash(earring, weight, specie, farm, gender, father, mother, bornAt, breastFeeding, status);
    }
}