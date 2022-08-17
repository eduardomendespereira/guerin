package br.com.guerin.Service;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import br.com.guerin.Repository.CattleEvent.CattleEventRepository;
import br.com.guerin.Service.IService.ICattleEventService;
import br.com.guerin.Service.IService.IEventTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CattleEventService implements ICattleEventService {
    private final CattleEventRepository cattleEventRepository;

    private final IEventTypeService eventTypeService;

    public Page<CattleEvent> findAll(Pageable pageable) {
        return cattleEventRepository.findAll(pageable);
    }

    public Optional<CattleEvent> findById(Long id) {
        return cattleEventRepository.findById(id);
    }
    @Transactional
    public CattleEvent save(CattleEvent cattleEvent) {
        return cattleEventRepository.save(cattleEvent);
    }
    @Transactional
    public void disable(Long id) {
        if (!this.findById(id).get().isInactive()) {
            cattleEventRepository.disable(id);
        }
    }

    public List<CattleEvent> findByEventType(Long eventTypeId) {
        EventType eventType = this.eventTypeService.findById(eventTypeId).get();
        return cattleEventRepository.findByEventType(eventType);
    }

    public Optional<CattleEvent> findByWeighing(Long weighing_id) {
        return cattleEventRepository.findByWeighing(weighing_id);
    }

    public Optional<CattleEvent> findByVaccineApp(Long vaccination_id) {
        return cattleEventRepository.findByVaccineApp(vaccination_id);
    }

    public ArrayList<CattleEvent> findByCattle(Long cattle_id) {
        return cattleEventRepository.findByCattle(cattle_id);
    }

    @Transactional
    public CattleEvent update(Long id, CattleEvent cattleEvent){
        if(id == cattleEvent.getId()){
            return this.cattleEventRepository.save(cattleEvent);
        }else{
            throw new RuntimeException("Evento não encontrado");
        }
    }
}
