package br.com.guerin.Repository.VaccineApplication;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Entity.VaccineApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface VaccineApplicationRepository extends JpaRepository<VaccineApplication, Long> {

    @Modifying
    @Query("UPDATE VaccineApplication va SET va.inactive = true WHERE va.id = :idVaccineApplication")
    void disable(@Param("idVaccineApplication") Long idVaccineApplication);

    @Query("SELECT va FROM VaccineApplication va where va.vaccine = :vaccine")
    Optional<ArrayList<VaccineApplication>> findByVaccine(Vaccine vaccine);

    @Query("SELECT va FROM VaccineApplication va where va.note = :note")
    Optional<VaccineApplication> findByNote(String note);

    @Query("SELECT va FROM VaccineApplication va WHERE va.cattle = :cattle AND va.vaccine = :vaccine" +
            " AND va.date = :dataApp")
    List<VaccineApplication> findDuplicateApplication(@Param("cattle") Cattle cattle,
                                                      @Param("vaccine") Vaccine vaccine,
                                                      @Param("dataApp") LocalDateTime dataApp);
}
