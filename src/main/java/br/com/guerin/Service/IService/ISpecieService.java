package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface ISpecieService {
    Specie save(Specie specie);
    Specie update(Long id, Specie specie);
    ArrayList<Specie> listAll();
    Optional<Specie> findById(Long id);
    void disable(Long id, Specie specie);
    Optional<Specie> findByName(String name);
    public Integer count();
}
