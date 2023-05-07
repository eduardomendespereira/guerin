package br.com.guerin.Repository.CattleEvent;

import br.com.guerin.Entity.*;
import org.springframework.data.domain.Page;
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

    @Modifying
    @Query("UPDATE CattleEvent ce SET ce.inactive = false WHERE ce.id = :id")
    void enable(@Param("id") Long cattleId);

    @Query("SELECT ce FROM CattleEvent ce where ce.eventType = :eventType")
    ArrayList<CattleEvent> findByEventType(EventType eventType);

    @Query("SELECT ce FROM CattleEvent ce where ce.weighing = :weighing")
    ArrayList<CattleEvent> findByWeighing(Weighing weighing);

    @Query("SELECT ce FROM CattleEvent ce where ce.vaccineApplication = :vaccination")
    ArrayList<CattleEvent> findByVaccineApp(VaccineApplication vaccination);

    @Query("SELECT ce FROM CattleEvent ce where ce.vaccineApplication.id = :vaccinationId")
    Optional<CattleEvent> findByVaccineApplication(Long vaccinationId);

    @Query("SELECT ce FROM CattleEvent ce where ce.weighing.id = :weighingId")
    Optional<CattleEvent> findByWeighingById(Long weighingId);

    @Query("SELECT ce FROM CattleEvent ce where ce.insemination.id = :inseminationId")
    Optional<CattleEvent> findByInseminationById(Long inseminationId);

    @Query("SELECT ce FROM CattleEvent ce where ce.cattle = :cattle")
    ArrayList<CattleEvent> findByCattle(Cattle cattle);

    @Query("SELECT ce FROM CattleEvent ce where ce.description = :descCattleEvent")
    Optional<CattleEvent> findByName(String descCattleEvent);

    @Query("select ce.cattle.id as cattleId from CattleEvent ce group by ce.cattle")
    ArrayList<Long> findAllAgrouped();
}
