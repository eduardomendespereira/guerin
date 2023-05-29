package br.com.guerin.Service;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Repository.VaccineApplication.VaccineApplicationRepository;
import br.com.guerin.Service.IService.IGenerateAutomaticEvent;
import br.com.guerin.Service.IService.IVaccineApplicationService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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
public class VaccineApplicationService implements IVaccineApplicationService {

    private final VaccineApplicationRepository vaccineApplicationRepository;

    private final IGenerateAutomaticEvent generateAutomaticEvent;

    public Optional<VaccineApplication> findById(Long id){
        Optional<VaccineApplication> va = this.vaccineApplicationRepository.findById(id);
        if (va.isPresent())
            return va;
        else {
            throw new RuntimeException("Aplicação de vacina não encontrada");
        }
    }
    public Optional<ArrayList<VaccineApplication>> findByVaccine(Vaccine vaccine){
        return this.vaccineApplicationRepository.findByVaccine(vaccine);
    }

    public Optional<VaccineApplication> findByNote(String note){
        return this.vaccineApplicationRepository.findByNote(note);
    }
    public ArrayList<VaccineApplication> findAll(){
        return (ArrayList<VaccineApplication>) vaccineApplicationRepository.findAll();
    }

    @Transactional
    public VaccineApplication saveTransactional(VaccineApplication vaccineApplication){
        return this.vaccineApplicationRepository.save(vaccineApplication);
    }

    public VaccineApplication update(Long id, VaccineApplication vaccineApplication){
        System.out.println(vaccineApplication);
        if(id.longValue() == vaccineApplication.getId()){
            System.out.println(vaccineApplication);
            return saveTransactional(vaccineApplication);
        }else{
            throw new RuntimeException("Aplicação de Vacina não encontrado");
        }
    }

    public VaccineApplication save(VaccineApplication vaccineApplication){
        if(validateSaveAndUpdate(vaccineApplication) && !this.vaccineApplicationRepository.findById(vaccineApplication.getId()).isPresent()){
            generateAutomaticEvent.generateCattleEventVaccination(vaccineApplication);
            return saveTransactional(vaccineApplication);
        }else {
            throw new DuplicateRequestException();
        }
    }

    @Transactional
    public void disable(Long id){
        if(!this.vaccineApplicationRepository.findById(id).get().isInactive()) {
            this.vaccineApplicationRepository.disable(id);
        }else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void enable(Long id){
        if(this.vaccineApplicationRepository.findById(id).get().isInactive()) {
            this.vaccineApplicationRepository.enable(id);
        }else {
            throw new RuntimeException();
        }
    }

    public boolean validateSaveAndUpdate(VaccineApplication vaccineApplication){
        if(vaccineApplicationRepository.findDuplicateApplication(vaccineApplication.getCattle(),
                vaccineApplication.getVaccine(), vaccineApplication.getDate()).size() == 0){
            return true;
        }else{
            throw new RuntimeException("Erro: Vacina " + vaccineApplication.getVaccine().getName() + "já aplicada nessa nada");
        }
    }

    public Integer count(){
        Integer count = 0;
        for(VaccineApplication vaccineApplication : vaccineApplicationRepository.findAll()){
            if(!vaccineApplication.isInactive()){
                count++;
            }
        }
        return count;
    }
}
