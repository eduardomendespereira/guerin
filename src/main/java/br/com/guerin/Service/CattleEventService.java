package br.com.guerin.Service;

import br.com.guerin.Entity.*;
import br.com.guerin.Repository.CattleEvent.CattleEventRepository;
import br.com.guerin.Service.IService.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CattleEventService implements ICattleEventService {
    private final CattleEventRepository cattleEventRepository;
    private final IEventTypeService eventTypeService;
    private final IWeighingService weighingService;
    private final IVaccineApplicationService vaccineApplicationService;
    private final ICattleService cattleService;
    public ArrayList<CattleEvent> findAll() {
        return (ArrayList<CattleEvent>) cattleEventRepository.findAll();
    }

    public Optional<CattleEvent> findById(Long id) {
        return cattleEventRepository.findById(id);
    }
    @Transactional
    public CattleEvent save(CattleEvent cattleEvent) {
        if(!validateTypeEvent(cattleEvent)){
           throw new RuntimeException("O evento possui 2 tipos de evento (vacinação e pesagem)");
        }else{
            return cattleEventRepository.save(cattleEvent);
        }
    }
    @Transactional
    public void disable(Long id) {
        if (!this.findById(id).get().isInactive()) {
            cattleEventRepository.disable(id);
        }
    }

    @Transactional
    public void enable(Long id) {
        if (this.findById(id).get().isInactive()) {
            cattleEventRepository.enable(id);
        }
    }

    public ArrayList<CattleEvent> findByEventType(Long eventTypeId) {
        EventType eventType = this.eventTypeService.findById(eventTypeId).get();
        return cattleEventRepository.findByEventType(eventType);
    }

    public ArrayList<CattleEvent> findByWeighing(Long weighingId) {
        var weighing = this.weighingService.findById(weighingId);
        return cattleEventRepository.findByWeighing(weighing);
    }

    public ArrayList<CattleEvent> findByVaccineApp(Long vaccinationId) {
        VaccineApplication vaccination = this.vaccineApplicationService.findById(vaccinationId).get();
        return cattleEventRepository.findByVaccineApp(vaccination);
    }

    public Optional<CattleEvent> findByVaccineApplication(Long id){
        return cattleEventRepository.findByVaccineApplication(id);
    }

    public Optional<CattleEvent> findByWeighingById(Long id){
        return cattleEventRepository.findByWeighingById(id);
    }

    public ArrayList<CattleEvent> findByCattle(Long cattleId) {
        Cattle cattle = this.cattleService.findById(cattleId).get();
        return cattleEventRepository.findByCattle(cattle);
    }

    @Transactional
    public CattleEvent update(Long id, CattleEvent cattleEvent){
        if(id == cattleEvent.getId() && validateTypeEvent(cattleEvent)){
            return this.cattleEventRepository.save(cattleEvent);
        }else{
            throw new RuntimeException("Evento não encontrado");
        }
    }

    public boolean validateTypeEvent(CattleEvent cattleEvent){
        if(cattleEvent.getVaccineApplication() != null && cattleEvent.getWeighing() != null){
            return false;
        }else {
            return true;
        }
    }

    public Optional<CattleEvent> findByName(String name){
        if (name != null){
            return this.cattleEventRepository.findByName(name);
        }else {
            throw new RuntimeException("Descrição do evento não encontrado!");
        }
    }
    public ArrayList<CattleEventAgrouped> findAllAgrouped(){
        var obj = cattleEventRepository.findAllAgrouped();
        var result = new ArrayList<CattleEventAgrouped>();
        for (Long id : obj) {
            var cattle = cattleService.findById(id);
            if (!cattle.isPresent())
                continue;
            var events = cattleEventRepository.findByCattle(cattle.get());
            var res = new CattleEventAgrouped("span", "Brinco: " + cattle.get().getEarring().toString(), true, events.stream().count(), events);
            result.add(res);
        }
        return result;
    }
    public Integer count(){
        Integer count = 0;
        for(CattleEvent cattleEvent : cattleEventRepository.findAll()){
            if(!cattleEvent.isInactive()){
                count++;
            }
        }
        return count;
    }
}
