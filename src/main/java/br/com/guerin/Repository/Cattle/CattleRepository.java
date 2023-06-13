package br.com.guerin.Repository.Cattle;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Entity.Farm;
import br.com.guerin.Entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import DTO.Cattle.LactatingCattleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> {
    @Modifying
    @Query("UPDATE Cattle cattle SET cattle.inactive = true WHERE cattle.earring = :earring")
    void disable(Long earring);

    @Modifying
    @Query("UPDATE Cattle cattle SET cattle.inactive = false WHERE cattle.earring = :earring")
    void enable(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.earring = :earring")
    Optional<Cattle> findByEarring(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.mother = :earring or cattle.father = :earring")
    ArrayList<Cattle> findChildren(Long earring);

    @Query("SELECT cattle FROM Cattle cattle where cattle.specie = :specie")
    ArrayList<Cattle> findBySpecie(Specie specie);

    @Query("SELECT cattle FROM Cattle cattle where cattle.farm = :farm")
    ArrayList<Cattle> findByFarm(Farm farm);

    @Query(value = """
            SELECT
                c.id,
                c.earring,
                (
                    SELECT COUNT(id)
                    FROM cattles
                    WHERE cattles.mother = c.earring
                    AND cattles.born_at >= (CURRENT_DATE - INTERVAL '40 days')
                ) as lactatingChildren,
                (c.last_breeding + INTERVAL '40 days') as lactationEndDate
            FROM
                cattles c
            WHERE
                EXISTS (
                    SELECT 1
                    FROM cattles
                    WHERE mother = c.earring
                    AND born_at >= (CURRENT_DATE - INTERVAL '40 days')
                )
            """, nativeQuery = true)
    List<Object[]> findLactatingCattles();
}