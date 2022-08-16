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
    void disable(@Param("cattleId") Long cattleId);

    @Query("SELECT cattle FROM Cattle cattle where cattle.earring = :earring")
    Optional<Cattle> findByEarring(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.mother = :earring or cattle.father = :earring")
    ArrayList<Cattle> findChildren(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.specie = :specie_id")
    ArrayList<Cattle> findBySpecie(Long specie_id);

    @Query("SELECT cattle FROM Cattle cattle where cattle.farm = :farm_id")
    ArrayList<Cattle> findByFarm(Long farm_id);
}
