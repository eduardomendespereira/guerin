package br.com.guerin.Repository.Farm;


import br.com.guerin.Entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    @Modifying
    @Query("UPDATE Farm farm SET farm.inactive = true WHERE farm.id = :farmId")
    void disable(@Param("farmId") Long farmId);

    @Modifying
    @Query("UPDATE Farm farm SET farm.inactive = false WHERE farm.id = :farmId")
    void enable(@Param("farmId") Long farmId);

    @Query("SELECT farm FROM Farm farm WHERE farm.name = :farmName")
    Optional<Farm> findByName(@Param("farmName") String farmName);

    @Query("SELECT farm FROM Farm farm WHERE farm.address = :farmAddress")
    Optional<Farm> findByAddress(@Param("farmAddress") String farmAddress);
}
