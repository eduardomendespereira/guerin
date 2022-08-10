package br.com.guerin.Service;

import br.com.guerin.Entity.User;
import br.com.guerin.Repository.User.UserRepository;
import br.com.guerin.Service.IService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void delete (Long id) { userRepository.deleteById(id); }
}