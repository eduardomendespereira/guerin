package br.com.guerin.Repository.User;
import br.com.guerin.Entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
}
