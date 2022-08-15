package br.com.guerin.Repository.Vaccine;
import br.com.guerin.Entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    public Vaccine findVaccineByName(String name);

    @Modifying
    @Query("UPDATE Vaccine vaccine SET vaccine.inactive = true WHERE vaccine.id = :idVaccine")
    public void disable(@Param("idVaccine") Long idVaccine);
}
