package br.com.guerin.Service.IService;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISpecieService {
    Specie save(Specie specie);
    Specie update(Long id, Specie specie);
    Page<Specie> listAll(Pageable pageable);
    Optional<Specie> findById(Long id);
    void desativar(Long id, Specie specie);
    Optional<Specie> findByName(String name);
}
