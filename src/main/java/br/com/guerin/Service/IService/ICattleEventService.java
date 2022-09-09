package br.com.guerin.Service.IService;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ICattleEventService {

    Page<CattleEvent> findAll(Pageable pageable);

    Optional<CattleEvent> findById(Long id);

    CattleEvent save(CattleEvent cattle);

    void disable(Long id);

    ArrayList<CattleEvent> findByEventType(Long eventTypeId);

    Optional<CattleEvent> findByWeighing(Long weighing_id);

    ArrayList<CattleEvent> findByVaccineApp(Long vaccination_id);

    ArrayList<CattleEvent> findByCattle(Long cattle_id);

    CattleEvent update(Long id, CattleEvent cattleEvent);

    Optional<CattleEvent> findByName(String name);
}
