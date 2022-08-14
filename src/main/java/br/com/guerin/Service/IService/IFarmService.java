package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IFarmService {
    void inactivate(Long id, Farm farm);
    Optional<Farm> findById(Long id);
    Optional<Farm> findByName(String name);
    Page<Farm> findAll(Pageable pageable);
    Farm update(Long id, Farm farm);
    Farm save(Farm farm);
}
