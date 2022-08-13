package br.com.guerin.Service.IService;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISpecie {
    void save(Specie specie);
    void inactivate(Long id, Specie specie);
    void update(Long id ,Specie specie);
    Specie findById(Long id);
    Page<Specie> listAll(Pageable pageable);
}
