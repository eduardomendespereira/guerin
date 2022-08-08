package br.com.guerin.Repository.User;

import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VaccineApplicationRepository extends JpaRepository<VaccineApplication, Long> {

    @Modifying
    @Query("UPDATE VaccineApplication va SET va.inactive = true WHERE va.id = :idVaccineApplication")
    public void disable(@Param("idVaccineApplication") Long idVaccineApplication);
}
