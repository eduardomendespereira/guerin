package br.com.guerin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "especie", schema = "public")
public class Specie extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 30)
    private String nome;
}
