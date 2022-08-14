package br.com.guerin.Repository.User;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Entity.User;
import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VaccineApplicationRepository extends JpaRepository<VaccineApplication, Long> {

    @Modifying
    @Query("UPDATE VaccineApplication va SET va.inactive = true WHERE va.id = :idVaccineApplication")
    void disable(@Param("idVaccineApplication") Long idVaccineApplication);

    @Query("SELECT va FROM VaccineApplication va where va.cattle = :id")
    Optional<VaccineApplication> findByCattle(Long id);

    @Query("SELECT va FROM VaccineApplication va where va.vaccine = :id")
    Optional<VaccineApplication> findByVaccine(Long id);
}
