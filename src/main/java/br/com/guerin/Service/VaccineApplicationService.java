package br.com.guerin.Service;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Repository.Vaccine.VaccineApplicationRepository;
import br.com.guerin.Service.IService.ICattleEventService;
import br.com.guerin.Service.IService.IEventTypeService;
import br.com.guerin.Service.IService.IVaccineApplicationService;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VaccineApplicationService implements IVaccineApplicationService {

    @Autowired
    private VaccineApplicationRepository vaccineApplicationRepository;

    private IEventTypeService eventTypeS;

    public IEventTypeService eventTypeService(final IEventTypeService service){
       return this.eventTypeS = service;
    }
    private ICattleEventService cattleEventS;

    public ICattleEventService cattleEventService(ICattleEventService cattleEventS){
        return this.cattleEventS = cattleEventS;
    }

    public Optional<VaccineApplication> findById(Long id){
        return this.vaccineApplicationRepository.findById(id);
    }
    public Optional<ArrayList<VaccineApplication>> findByVaccine(Vaccine vaccine){
        return this.vaccineApplicationRepository.findByVaccine(vaccine);
    }

    public Optional<VaccineApplication> findByNote(String note){
        return this.vaccineApplicationRepository.findByNote(note);
    }
    public Page<VaccineApplication> findAll(Pageable pageable){
        return this.vaccineApplicationRepository.findAll(pageable);
    }

    @Transactional
    public VaccineApplication saveTransactional(VaccineApplication vaccineApplication){
        return this.vaccineApplicationRepository.save(vaccineApplication);
    }

    public VaccineApplication update(Long id, VaccineApplication vaccineApplication){
        return saveTransactional(vaccineApplication);
    }

    public VaccineApplication save(VaccineApplication vaccineApplication){
       // if(validateSaveAndUpdate(vaccineApplication)){
            //generateCattleEventVaccination(vaccineApplication);
            return saveTransactional(vaccineApplication);
        //}else {
         //   throw new DuplicateRequestException();
        //}
    }

    @Transactional
    public void disable(Long id, VaccineApplication vaccineApplication){
        if(id == vaccineApplication.getId()) {
            this.vaccineApplicationRepository.disable(vaccineApplication.getId());
        }else {
            throw new RuntimeException();
        }
    }

    public boolean validateSaveAndUpdate(VaccineApplication vaccineApplication){
        if(vaccineApplicationRepository.findDuplicateApplication(vaccineApplication.getCattle(),
                vaccineApplication.getVaccine(), vaccineApplication.getDate()).size() == 0){
            return true;
        }else{
            throw new RuntimeException("Erro: Vacina {vaccineApplication.getVaccine().getName()} já aplicada nessa nada");
        }
    }

    private EventType generateEventTypeVaccination(){
        EventType eventTypeVaccination = new EventType(
                "Aplicação de Vacina"
        );
//        if(!eventTypeService.findByName(eventTypeVaccination.getName()).isPresent()){
//            eventTypeService.save(eventTypeVaccination);
//        }
//        return eventTypeService.findByName(eventTypeVaccination.getName()).get();
        if (!eventTypeService(eventTypeS).findByName(eventTypeVaccination.getName()).isPresent()){
            eventTypeService(eventTypeS).save(eventTypeVaccination);
        }
        return eventTypeService(eventTypeS).findByName(eventTypeVaccination.getName()).get();
    }

    public void generateCattleEventVaccination(VaccineApplication vaccineApplication) {
        EventType getEventType = this.generateEventTypeVaccination();
        CattleEvent cattleEventVaccination = new CattleEvent(
                vaccineApplication.getCattle(),
                getEventType,
                vaccineApplication.getDate(),
                "Aplicação de vacina {vaccineApplication.getVaccine().getName()}",
                vaccineApplication
        );
//        this.cattleEventService.save(cattleEventVaccination);
        cattleEventService(cattleEventS).save(cattleEventVaccination);
    }
}
