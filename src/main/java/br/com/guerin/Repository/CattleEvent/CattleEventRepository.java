package br.com.guerin.Repository.CattleEvent;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface CattleEventRepository extends JpaRepository<CattleEvent, Long> {
    @Modifying
    @Query("UPDATE CattleEvent ce SET ce.inactive = true WHERE ce.id = :id")
    void disable(@Param("id") Long cattleId);

    @Query("SELECT ce FROM CattleEvent ce where ce.eventType = :eventType_id")
    ArrayList<CattleEvent> findByEventType(Long eventType_id);

    @Query("SELECT ce FROM CattleEvent ce where ce.weighing = :weighing_id")
    Optional<CattleEvent> findByWeighing(Long weighing_id);

    @Query("SELECT ce FROM CattleEvent ce where ce.vaccineApplication = :vaccination_id")
    Optional<CattleEvent> findByVaccineApp(Long vaccination_id);

    @Query("SELECT ce FROM CattleEvent ce where ce.cattle = :cattle_id")
    ArrayList<CattleEvent> findByCattle(Long cattle_id);
}
