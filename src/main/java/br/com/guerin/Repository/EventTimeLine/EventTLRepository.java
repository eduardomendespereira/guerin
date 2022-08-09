package br.com.guerin.Repository.EventTimeLine;

import br.com.guerin.Entity.EventTimeLine;
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
public interface EventTLRepository extends JpaRepository<EventTimeLine, Long> {
    /**
     *
     * @param idEventTL
     */
    @Modifying
    @Query("UPDATE EventTimeLine eventTimeLine " +
            "SET eventTimeLine.inactive = :true " +
            "WHERE eventTimeLine.id = :idEventTL")
    public void disable( @Param("idEventTL") Long idEventTL);
}
