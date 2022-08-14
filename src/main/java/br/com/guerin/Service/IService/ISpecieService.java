package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISpecieService {
    void save(Specie specie);
    Specie findById(Long id);
    Optional<Specie> findByName(String name);
    Page<Specie> listAll(Pageable pageable);
    void update(Long id, Specie specie);
    void desativar(Long id, Specie specie);
    boolean checkAtivo(Specie specie);
}
