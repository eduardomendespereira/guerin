package br.com.uniamerica.api.controlegado.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 22/07/2022
 * @version 1.0.0
 */
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "cadastro", nullable = false)
    private LocalDateTime cadastro;

    @Getter
    @Column(name = "atualizado")
    private LocalDateTime atualizado;

    @Getter @Setter
    @Column(name = "ativo", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean ativo;

    /**
     *
     * @param id
     */
    public AbstractEntity(Long id){
        this.id = id;
    }

    /**
     * Método executado antes da execução repository.save
     */
    @PrePersist
    public void dataCadastro(){
        this.cadastro = LocalDateTime.now();
    }

    /**
     * Método executado antes da execução repository.update
     */
    @PreUpdate
    public void dataAtualizacao(){
        this.atualizado = LocalDateTime.now();
    }

}