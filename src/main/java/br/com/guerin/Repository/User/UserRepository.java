package br.com.guerin.Repository.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.guerin.Entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u where u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u where u.username = :username")
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.inactive = true WHERE u.id = :id")
    void disable(Long id);
}