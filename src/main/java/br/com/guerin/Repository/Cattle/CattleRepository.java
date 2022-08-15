package br.com.guerin.Repository.Cattle;

import br.com.guerin.Entity.Cattle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> {
    @Modifying
    @Query("UPDATE Cattle cattle SET cattle.inactive = true WHERE cattle.id = :cattleId")
    void inactivate(@Param("cattleId") Long cattleId);
    @Query("SELECT c FROM Cattle c where c.earring = :earring")
    Optional<Cattle> findByEarring(Long earring);
    @Query("SELECT c FROM Cattle c where c.mother = :earring or c.father = :earring")
    ArrayList<Cattle> findSons(Long earring);
    @Query("SELECT c FROM Cattle c where c.specie = :specie_id")
    ArrayList<Cattle> findBySpecie(Long specie_id);
    @Query("SELECT c FROM Cattle c where c.farm = :farm_id")
    ArrayList<Cattle> findByFarm(Long farm_id);
}
