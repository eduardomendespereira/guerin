package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Vaccine;
<<<<<<< HEAD
import br.com.guerin.Repository.User.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccineService{
    void disable(Long id, Vaccine vaccine);
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface IVaccineService{
    void disable(Long id, Vaccine vaccine);
    Optional<Vaccine> findById(Long id);
    Page<Vaccine> findAll(Pageable pageable);
    Vaccine update(Long id, Vaccine vaccine);
    Vaccine saveTransactional(Vaccine vaccine);
    Vaccine save(Vaccine vaccine);
>>>>>>> main
}
