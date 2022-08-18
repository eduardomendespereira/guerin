package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.Vaccine.VaccineRepository;
import br.com.guerin.Service.IService.IVaccineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VaccineService implements IVaccineService{

    private final VaccineRepository vaccineRepository;

    public Optional<Vaccine> findById(Long id){
        return this.vaccineRepository.findById(id);
    }

    public Page<Vaccine> findAll(Pageable pageable){
        return this.vaccineRepository.findAll(pageable);
    }

    public Vaccine update(Long id, Vaccine vaccine){
        return saveTransactional(vaccine);
    }

    @Transactional
    public Vaccine saveTransactional(Vaccine vaccine){
        return this.vaccineRepository.save(vaccine);
    }

    public Vaccine save(Vaccine vaccine){
        return saveTransactional(vaccine);
    }

    public Optional<Vaccine> findByName(String name) {
        return vaccineRepository.findByName(name);
    }

    @Transactional
    public void disable(Long id){
        if(!this.vaccineRepository.findById(id).get().isInactive()){
            this.vaccineRepository.disable(id);
        }
    }
}
