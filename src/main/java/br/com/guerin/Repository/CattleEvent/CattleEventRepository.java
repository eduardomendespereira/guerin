package br.com.guerin.Repository.CattleEvent;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Entity.Weighing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CattleEventRepository extends JpaRepository<CattleEvent, Long> {
    @Modifying
    @Query("UPDATE CattleEvent ce SET ce.inactive = true WHERE ce.id = :id")
    void disable(@Param("id") Long cattleId);

    @Query("SELECT ce FROM CattleEvent ce where ce.eventType = :eventType")
    List<CattleEvent> findByEventType(EventType eventType);

    @Query("SELECT ce FROM CattleEvent ce where ce.weighing = :weighing")
    List<CattleEvent> findByWeighing(Weighing weighing);

    @Query("SELECT ce FROM CattleEvent ce where ce.vaccineApplication = :vaccination")
    Optional<CattleEvent> findByVaccineApp(VaccineApplication vaccination);

    @Query("SELECT ce FROM CattleEvent ce where ce.cattle = :cattle_id")
    ArrayList<CattleEvent> findByCattle(Long cattle_id);
}
