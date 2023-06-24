package br.com.guerin.Repository.Specie;

import br.com.guerin.Entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, Long> {
    @Modifying
    @Query("UPDATE Specie specie SET specie.inactive = true WHERE specie.id = :idSpecie")
    void desativar(@Param("idSpecie") Long idSpecie);

    @Query("SELECT s FROM Specie s where s.name = :name")
    Optional<Specie> findByName(String name);

    @Modifying
    @Query("UPDATE Specie  specie SET specie.inactive = false WHERE specie.id = :idSpecie")
    void enable(@Param("idSpecie") Long idSpecie);
}
