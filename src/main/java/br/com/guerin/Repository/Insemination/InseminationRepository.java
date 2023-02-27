package br.com.guerin.Repository.Insemination;

import br.com.guerin.Entity.Insemination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InseminationRepository extends JpaRepository<Insemination, Long> {

    @Modifying
    @Query("UPDATE Insemination insemination SET insemination.inactive = true WHERE insemination.id = :idInsemination")
    void disable(@Param("idInsemination") Long idInsemination);

    @Modifying
    @Query("UPDATE Insemination insemination SET insemination.inactive = false WHERE insemination.id = :idInsemination")
    void enable(@Param("idInsemination") Long idInsemination);
}
