package br.com.guerin.Service.IService;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISpecieService {
    void save(Specie specie);
    void update(Long id, Specie specie);
    Page<Specie> listAll(Pageable pageable);
    Specie findById(Long id);
    void desativar(Long id, Specie specie);
    Specie findByName(String name);
}
