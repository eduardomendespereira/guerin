package br.com.guerin.Repository.Insemination;

import br.com.guerin.Entity.Insemination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InseminationRepository extends JpaRepository<Insemination, Long> {
}
