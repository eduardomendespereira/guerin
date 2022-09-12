package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IFarmService {
    Farm disable(Long id, Farm farm);
    Optional<Farm> findByName(String name);
    Optional<Farm> findByAddress(String address);
    Farm save(Farm farm);
    Farm update(Long id, Farm farm);
    Page<Farm> findAll(Pageable pageable);
    Optional<Farm> findById(Long id);
}
