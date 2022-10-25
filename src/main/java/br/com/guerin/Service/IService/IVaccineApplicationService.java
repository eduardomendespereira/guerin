package br.com.guerin.Service.IService;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface IVaccineApplicationService {
    void disable(Long id);
    void enable(Long id);
    Optional<VaccineApplication> findById(Long id);
    Page<VaccineApplication> findAll(Pageable pageable);
    VaccineApplication saveTransactional(VaccineApplication vaccineApplication);
    VaccineApplication update(Long id, VaccineApplication vaccineApplication);
    VaccineApplication save(VaccineApplication vaccineApplication);
    Optional<ArrayList<VaccineApplication>> findByVaccine(Vaccine vaccine);
    Optional<VaccineApplication> findByNote(String note);
    boolean validateSaveAndUpdate(VaccineApplication vaccineApplication);
    public Integer count();
}
