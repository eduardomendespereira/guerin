package br.com.guerin.Repository.Parameter;

import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    @Query("SELECT p FROM Parameter p where p.id = :id")
    Optional<Parameter> findById(String id);
    @Modifying
    @Query("delete from Parameter p where p.id = :id")
    void deleteById(String id);
}
