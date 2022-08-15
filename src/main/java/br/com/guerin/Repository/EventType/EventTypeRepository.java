package br.com.guerin.Repository.EventType;

import br.com.guerin.Entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    @Modifying
    @Query("UPDATE EventType eventype SET eventype.inactive = true " +
            "WHERE eventype.id = :idEventType")
    void desativar(@Param("idEventType") Long idEventType);
}
