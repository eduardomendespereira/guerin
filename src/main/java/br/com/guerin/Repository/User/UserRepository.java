package br.com.guerin.Repository.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.guerin.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);
    User findByEmail(String email);
}