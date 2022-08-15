package br.com.guerin.Repository.Weighing;

import br.com.guerin.Entity.Weighing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Gabriel Luiz C
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */

@Repository
public interface WeighingRepository extends JpaRepository<Weighing, Long> {
    /**
     *
     * @param idWeighing
     */
    @Modifying
    @Query("UPDATE Weighing weighing " +
            "SET weighing.inactive = true " +
            "WHERE weighing.id = :idWeighing")
    void disable( @Param("idWeighing") Long idWeighing);
}
