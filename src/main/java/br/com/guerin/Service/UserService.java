package br.com.guerin.Service;

import br.com.guerin.Entity.User;
import br.com.guerin.Repository.User.UserRepository;
import br.com.guerin.Service.IService.INotificationService;
import br.com.guerin.Service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import DTO.Notification.Notification;
import DTO.Notification.NotificationType;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Not Found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRole().value));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), authorities);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public ArrayList<User> findAll() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Transactional
    public User save(User user, NotificationService notificationService) {        
        validateUser(user, notificationService);

        if (user.getId() == null && user.getUsername() != null && findByUsername(user.getUsername()).isPresent()) {
            notificationService.addNotification(new Notification("Username ja cadastrado!", NotificationType.ERROR));
        }

        if (notificationService.hasNotifications()) {
            return null;
        }

        if (!user.getPassword().startsWith("$2a$10"))
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void disable(Long id) {
        if (!this.findById(id).get().isInactive()) {
            userRepository.disable(id);
        }
    }

    @Transactional
    public void enable(Long id) {
        if (this.findById(id).get().isInactive()) {
            userRepository.enable(id);
        }
    }

    private void validateUser(User user, NotificationService notificationService) {
        validateField(user.getFirstName(), "O nome é obrigatório.", notificationService);
        validateField(user.getUsername(), "O usuario é obrigatório.", notificationService);
        validateField(user.getEmail(),"O e-mail é obrigatório.", notificationService);
        validateField(user.getPassword(), "A senha é obrigatória.", notificationService);
    }

    private void validateField(String fieldValue, String errorMessage, NotificationService notificationService) {
        if (Objects.isNull(fieldValue) || fieldValue.trim().isEmpty()) {
            Notification notification = new Notification(errorMessage, NotificationType.ERROR);
            notificationService.addNotification(notification);
        }
    }  
}