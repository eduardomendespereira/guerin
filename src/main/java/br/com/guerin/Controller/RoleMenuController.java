package br.com.guerin.Controller;

import br.com.guerin.Config.JwtConstants;
import br.com.guerin.Entity.Menu;
import br.com.guerin.Entity.Role;
import br.com.guerin.Entity.RoleMenu;
import br.com.guerin.Service.IService.IMenuService;
import br.com.guerin.Service.IService.IRoleMenuService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/api/rolemenu")
@RequiredArgsConstructor
public class RoleMenuController {
    private final IRoleMenuService roleMenuService;
    private final IMenuService menuService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleMenu roleMenu) {
        try {
            return ResponseEntity.ok().body(roleMenuService.save(roleMenu));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(roleMenuService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/menu/{id}")
    public ResponseEntity<?> findByMenu(@PathVariable Long id) {
        try {
            var menu = menuService.findById(id);
            if (!menu.isPresent())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(roleMenuService.findByMenu(menu.get()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/role")
    public ResponseEntity<?> findByRole(@RequestHeader("Authorization") String token) {
        try {
            String tk = token.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256(JwtConstants.SECRET.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(tk);
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            var role = Role.valueOf(Arrays.stream(roles).findFirst().get());
            return ResponseEntity.ok().body(roleMenuService.findByRole(role));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        roleMenuService.delete(id);
    }
    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(roleMenuService.findAll(pageable));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
