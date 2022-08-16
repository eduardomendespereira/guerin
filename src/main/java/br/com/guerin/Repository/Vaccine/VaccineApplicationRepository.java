package br.com.guerin.Repository.Vaccine;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VaccineApplicationRepository extends JpaRepository<VaccineApplication, Long> {

    @Modifying
    @Query("UPDATE VaccineApplication va SET va.inactive = true WHERE va.id = :idVaccineApplication")
    void disable(@Param("idVaccineApplication") Long idVaccineApplication);

    @Query("SELECT va FROM VaccineApplication va where va.vaccine = :id")
    Optional<Vaccine> findByVaccine(Long id);

    @Query("SELECT va FROM VaccineApplication va WHERE va.cattle = :idCattle AND va.vaccine = :idVaccine" +
            " AND va.date = :dataApp")
    List<VaccineApplication> findDuplicateApplication(@Param("idCattle") Long idCattle,
                                                      @Param("idVaccine") Long idVaccine,
                                                      @Param("dataApp") LocalDateTime dataApp);
}
