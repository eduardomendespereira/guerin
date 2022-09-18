package br.com.guerin.Repository.Cattle;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Specie;
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
    @Query("UPDATE Cattle cattle SET cattle.inactive = true WHERE cattle.earring = :earring")
    void disable(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.earring = :earring")
    Optional<Cattle> findByEarring(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.mother = :earring or cattle.father = :earring")
    ArrayList<Cattle> findChildren(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.specie = :specie")
    ArrayList<Cattle> findBySpecie(Specie specie);

    @Query("SELECT cattle FROM Cattle cattle where cattle.farm = :farm")
    ArrayList<Cattle> findByFarm(Farm farm);
}
