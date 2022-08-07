package br.com.guerin.Repository.User;

import br.com.guerin.Entity.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SpecieRepository extends JpaRepository<Specie, Long> {
}
