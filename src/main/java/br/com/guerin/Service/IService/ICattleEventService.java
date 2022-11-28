package br.com.guerin.Service.IService;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ICattleEventService {

    ArrayList<CattleEvent> findAll();

    Optional<CattleEvent> findById(Long id);

    CattleEvent save(CattleEvent cattle);
    void enable(Long id);
    void disable(Long id);

    ArrayList<CattleEvent> findByEventType(Long eventTypeId);

    ArrayList<CattleEvent> findByWeighing(Long weighing_id);

    ArrayList<CattleEvent> findByVaccineApp(Long vaccination_id);

    Optional<CattleEvent> findByVaccineApplication(Long vaccineApplicationId);

    Optional<CattleEvent> findByWeighingById(Long weighingId);

    ArrayList<CattleEvent> findByCattle(Long cattle_id);

    CattleEvent update(Long id, CattleEvent cattleEvent);

    Optional<CattleEvent> findByName(String name);

    public Integer count();
}
