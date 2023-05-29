package br.com.guerin.Service;

import br.com.guerin.Entity.User;
import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Entity.Weighing;
import br.com.guerin.Repository.Vaccine.VaccineRepository;
import br.com.guerin.Service.IService.IVaccineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
        Optional<Vaccine> vaccine = this.vaccineRepository.findById(id);
        if (vaccine.isPresent()){
            return vaccine;
        }else{
            throw new RuntimeException("A vacina informada não foi encontrada!");        }
    }

    public ArrayList<Vaccine> findAll() {
        return (ArrayList<Vaccine>) vaccineRepository.findAll();
    }

    public Vaccine update(Vaccine vaccine){
        return saveTransactional(vaccine);
    }

    @Transactional
    public Vaccine saveTransactional(Vaccine vaccine){
        return this.vaccineRepository.save(vaccine);
    }

    public Vaccine save(Vaccine vaccine){
        if(!this.vaccineRepository.findByName(vaccine.getName()).isPresent())
            return saveTransactional(vaccine);
        else {
            throw new RuntimeException("Vacina já cadastrada");
        }
    }

    public Optional<Vaccine> findByName(String name) {
        Optional<Vaccine> v = this.vaccineRepository.findByName(name);
        if (v.isPresent()){
            return v;
        }else{
            throw new RuntimeException("Vacina não encontrada");
        }
    }

    @Transactional
    public void disable(Long id){
        if(!this.vaccineRepository.findById(id).get().isInactive()){
            this.vaccineRepository.disable(id);
        }else{
            throw new RuntimeException("Vacina não encontrada");
        }
    }

    @Transactional
    public void enable(Long id){
        if(this.vaccineRepository.findById(id).get().isInactive()){
            this.vaccineRepository.enable(id);
        }else{
            throw new RuntimeException("Essa vacina já está ativa!");
        }
    }

    public Integer count(){
        Integer count = 0;
        for(Vaccine vaccine : vaccineRepository.findAll()){
            if(!vaccine.isInactive()){
                count++;
            }
        }
        return count;
    }
}
