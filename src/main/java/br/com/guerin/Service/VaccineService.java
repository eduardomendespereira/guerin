package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
import br.com.guerin.Service.IService.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VaccineService implements IVaccineService{

    @Autowired
    private VaccineRepository vaccineRepository;

    public Optional<Vaccine> findById(Long id){
        return this.vaccineRepository.findById(id);
    }

    public Page<Vaccine> findAll(Pageable pageable){
        return this.vaccineRepository.findAll(pageable);
    }

<<<<<<< HEAD
    public void update(Long id, Vaccine vaccine){
       saveTransactional(vaccine);
    }

    @Transactional
    public void saveTransactional(Vaccine vaccine){
        this.vaccineRepository.save(vaccine);
    }

    public void insert(Vaccine vaccine){
        saveTransactional(vaccine);
=======
    public Vaccine update(Long id, Vaccine vaccine){
      return saveTransactional(vaccine);
    }

    @Transactional
    public Vaccine saveTransactional(Vaccine vaccine){
       return this.vaccineRepository.save(vaccine);
    }

    public Vaccine save(Vaccine vaccine){
       return saveTransactional(vaccine);
>>>>>>> main
    }

    @Transactional
    public void disable(Long id, Vaccine vaccine){
        if(id == vaccine.getId()){
            this.vaccineRepository.disable(vaccine.getId());
        }
    }
}
