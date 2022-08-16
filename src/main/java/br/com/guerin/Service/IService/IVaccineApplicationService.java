package br.com.guerin.Service.IService;

import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IVaccineApplicationService {
    void disable(Long id, VaccineApplication vaccineApplication);
    Optional<VaccineApplication> findById(Long id);
    Page<VaccineApplication> findAll(Pageable pageable);
    VaccineApplication saveTransactional(VaccineApplication vaccineApplication);
    VaccineApplication update(Long id, VaccineApplication vaccineApplication);
    VaccineApplication save(VaccineApplication vaccineApplication);
    Optional<VaccineApplication> findByVaccine(Long id);
    boolean validateSaveAndUpdate(VaccineApplication vaccineApplication);
}
