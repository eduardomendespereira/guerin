package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IFarmService {
    void disable(Long id);
    Optional<Farm> findByName(String name);
    Farm save(Farm farm);
    Farm update(Long id, Farm farm);
    Page<Farm> findAll(Pageable pageable);
    Optional<Farm> findById(Long id);
}
