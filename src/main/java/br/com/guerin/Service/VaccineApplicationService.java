package br.com.guerin.Service;

<<<<<<< HEAD
import br.com.guerin.Entity.Vaccine;
=======
>>>>>>> main
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Repository.User.VaccineApplicationRepository;
import br.com.guerin.Service.IService.IVaccineApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

=======
import org.springframework.data.domain.Pageable;
>>>>>>> main
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */
@Service
public class VaccineApplicationService implements IVaccineApplicationService {

    @Autowired
    private VaccineApplicationRepository vaccineApplicationRepository;

    public Optional<VaccineApplication> findById(Long id){
        return this.vaccineApplicationRepository.findById(id);
    }
<<<<<<< HEAD

=======
    public Optional<VaccineApplication> findByVaccine(Long id){
        return this.vaccineApplicationRepository.findByVaccine(id);
    }
>>>>>>> main
    public Page<VaccineApplication> findAll(Pageable pageable){
        return this.vaccineApplicationRepository.findAll(pageable);
    }

    @Transactional
<<<<<<< HEAD
    public void saveTransactional(VaccineApplication vaccineApplication){
        this.vaccineApplicationRepository.save(vaccineApplication);
    }

    public void update(Long id, VaccineApplication vaccineApplication){
       saveTransactional(vaccineApplication);
    }

    public void insert(VaccineApplication vaccineApplication){
        saveTransactional(vaccineApplication);
=======
    public VaccineApplication saveTransactional(VaccineApplication vaccineApplication){
        return this.vaccineApplicationRepository.save(vaccineApplication);
    }

    public VaccineApplication update(Long id, VaccineApplication vaccineApplication){
       return saveTransactional(vaccineApplication);
    }

    public VaccineApplication save(VaccineApplication vaccineApplication){
        return saveTransactional(vaccineApplication);
>>>>>>> main
    }

    @Transactional
    public void disable(Long id, VaccineApplication vaccineApplication){
        if(id == vaccineApplication.getId()) {
            this.vaccineApplicationRepository.disable(vaccineApplication.getId());
        }else {
            throw new RuntimeException();
        }
    }
}
