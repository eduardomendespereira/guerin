package br.com.guerin.Controller;

import br.com.guerin.Config.JwtConstants;
import br.com.guerin.Payload.User.FindByEmailRequest;
import br.com.guerin.Payload.User.LoginRequest;
import br.com.guerin.Service.IService.IUserService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.guerin.Entity.User;
import io.jsonwebtoken.Jwts;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userService.save(user);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginRequest login) throws ServletException {

        String jwtToken = "";

        if (login.getEmail() == null || login.getPassword() == null) {
            throw new ServletException("Preencha o usuario e a senha");
        }

        String email = login.getEmail();
        String password = login.getPassword();

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new ServletException("Usuario nao localizado.");
        }

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Credenciais invalidas. Verifique o email e a senha.");
        }

        jwtToken = Jwts.builder().setSubject(email).claim("roles", user.getRole()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JwtConstants.SECRET).compact();

        return jwtToken;
    }
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public User findByEmail(@RequestBody FindByEmailRequest email) {
        return userService.findByEmail(email.getEmail());
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Page<User> findAll() {
        return userService.findAll(PageRequest.of(0, 100));
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteUser(@RequestBody Long id) {
        userService.delete(id);
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Optional<User> findById(@RequestBody Long id) {
        return userService.findById(id);
    }
}