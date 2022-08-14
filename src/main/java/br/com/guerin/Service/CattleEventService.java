package br.com.guerin.Service;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Repository.CattleEvent.CattleEventRepository;
import br.com.guerin.Repository.User.UserRepository;
import br.com.guerin.Service.IService.ICattleEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CattleEventService implements ICattleEventService {
    private final CattleEventRepository cattleEventRepository;

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

    public void disable(Long id) {
        if (!this.findById(id).get().isInactive()) {
            cattleEventRepository.disable(id);
        }
    }

    public ArrayList<CattleEvent> findByEventType(Long eventType_id) {
        return cattleEventRepository.findByEventType(eventType_id);
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
}
