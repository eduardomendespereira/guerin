package br.com.guerin.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "species", schema = "public")
public class Specie extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String name;
}
