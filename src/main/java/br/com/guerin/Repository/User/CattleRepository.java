package br.com.guerin.Repository.User;

import br.com.guerin.Entity.Cattle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CattleRepository extends JpaRepository<Cattle, Long> {
    @Modifying
    @Query("UPDATE Cattle cattle SET cattle.inactive = true WHERE cattle.id = :cattleId")
    public void inactivate(@Param("cattleId") Long cattleId);
}
