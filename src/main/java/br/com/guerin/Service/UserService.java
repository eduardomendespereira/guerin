package br.com.guerin.Service;
import br.com.guerin.Entity.User;
import br.com.guerin.Repository.User.UserRepository;
import  br.com.guerin.Service.IService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}