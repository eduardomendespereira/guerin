package br.com.guerin.Repository.Farm;


import br.com.guerin.Entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    @Modifying
    @Query("UPDATE Farm farm SET farm.inactive = true WHERE farm.id = :farmId")
    void inactivate(@Param("farmId") Long farmId);

    @Query("SELECT farm FROM Farm farm WHERE farm.name = :farmName")
    Farm findByName(@Param("farmName") String farmName);
}
