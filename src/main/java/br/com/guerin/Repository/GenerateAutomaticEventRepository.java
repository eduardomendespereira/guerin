package br.com.guerin.Repository;

import br.com.guerin.Entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenerateAutomaticEventRepository extends JpaRepository<EventType, Long> {
    @Query("SELECT et FROM EventType et where et.name = :nameEvent")
    Optional<EventType> findByName(@Param("nameEvent") String nameEvent);
}
