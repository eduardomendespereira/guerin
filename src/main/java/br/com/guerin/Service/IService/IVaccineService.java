package br.com.guerin.Service.IService;

import br.com.guerin.Entity.User;
import br.com.guerin.Entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;


public interface IVaccineService{
    void disable(Long id);
    void enable(Long id);
    Optional<Vaccine> findById(Long id);
    ArrayList<Vaccine> findAll();
    Vaccine update(Vaccine vaccine);
    Vaccine saveTransactional(Vaccine vaccine);
    Vaccine save(Vaccine vaccine);
    Optional<Vaccine> findByName(String name);
    public Integer count();


}
