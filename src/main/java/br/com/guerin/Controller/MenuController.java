package br.com.guerin.Controller;

import br.com.guerin.Entity.Menu;
import br.com.guerin.Service.IService.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final IMenuService menuService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Menu menu) {
        try {
            return ResponseEntity.ok().body(menuService.save(menu));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Menu menu) {
        try {
            return ResponseEntity.ok().body(menuService.save(menu));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(menuService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok().body(menuService.findByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        menuService.delete(id);
    }
    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(menuService.findAll(pageable));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
