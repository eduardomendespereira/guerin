package br.com.guerin.Repository.Menu;

import br.com.guerin.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.guerin.Entity.User;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT u FROM Menu u where u.name = :name")
    Optional<Menu> findByName(String name);

    @Modifying
    @Query("UPDATE Menu u SET u.inactive = true WHERE u.id = :id")
    void disable(Long id);
}