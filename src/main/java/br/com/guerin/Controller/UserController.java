package br.com.guerin.Controller;

import br.com.guerin.Config.JwtConstants;
import br.com.guerin.Service.NotificationService;
import br.com.guerin.Service.IService.IUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.Notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.guerin.Entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            NotificationService notificationService = new NotificationService();
            var createdUser = userService.save(user, notificationService);
            if (createdUser != null) {
                return ResponseEntity.ok("Usuário criado com sucesso.");
            } else if (notificationService.hasNotifications()) {
                List<Notification> notifications = notificationService.getNotifications();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notifications);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o Usuário.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> update(@RequestBody User user) {
        try {
            NotificationService notificationService = new NotificationService();
            var updatedUser = userService.save(user, notificationService);
            if (updatedUser != null) {
                return ResponseEntity.ok("Usuário criado com sucesso.");
            } else if (notificationService.hasNotifications()) {
                List<Notification> notifications = notificationService.getNotifications();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notifications);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o Usuário.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/get-by-email")
    public ResponseEntity<?> findByEmail(String email) {
        try {
            return ResponseEntity.ok().body(userService.findByEmail(email));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user/get-by-username")
    public ResponseEntity<?> findByUsername(String username) {
        try {
            return ResponseEntity.ok().body(userService.findByUsername(username));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(userService.findAll());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/user/disable/{id}")
    public void disable(@PathVariable("id") Long id) {
        userService.disable(id);
    }

    @GetMapping("/user/enable/{id}")
    public void enable(@PathVariable("id") Long id) {
        userService.enable(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(userService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handle() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(JwtConstants.SECRET.getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                Optional<User> user = userService.findByUsername(username);

                Collection<String> roles = new ArrayList<>();
                roles.add(user.get().getRole().value);

                String access_token = JWT.create()
                        .withSubject(user.get().getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000)) // 2 hrs
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", roles.stream().collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception ex) {
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh Token nao encontrado");
        }
    }
}