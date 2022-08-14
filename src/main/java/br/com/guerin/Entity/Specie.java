package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "species", schema = "public")
public class Specie extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specie)) return false;
        Specie specie = (Specie) o;
        return Objects.equals(name, specie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    public Specie(String name) {
        this.name = name;
    }
}
