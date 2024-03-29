package br.com.guerin.Repository.EventType;

import br.com.guerin.Entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    @Modifying
    @Query("UPDATE EventType eventype SET eventype.inactive = true " +
            "WHERE eventype.id = :idEventType")
    void desativar(@Param("idEventType") Long idEventType);

    @Query("SELECT et FROM EventType et where et.name = :nameEvent")
    Optional<EventType> findByName(@Param("nameEvent") String nameEvent);


    @Modifying
    @Query("UPDATE EventType  eventType SET eventType.inactive = false WHERE eventType.id = :idEventType")
    void enable(@Param("idEventType") Long idEventType);
}
