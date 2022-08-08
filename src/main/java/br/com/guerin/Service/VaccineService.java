package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Repository.User.VaccineRepository;
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
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public Optional<Vaccine> findById(Long id){
        return this.vaccineRepository.findById(id);
    }

    public Page<Vaccine> findAll(Pageable pageable){
        return this.vaccineRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, Vaccine vaccine){
        if(id == vaccine.getId()){
            this.vaccineRepository.save(vaccine);
        }else{
            throw  new RuntimeException();
        }
    }

    @Transactional
    public void insert(Vaccine vaccine){
        this.vaccineRepository.save(vaccine);
    }

    @Transactional
    public void disable(Long id, Vaccine vaccine){
        if(id == vaccine.getId()){
            this.vaccineRepository.disable(vaccine.getId());
        }
    }
}
