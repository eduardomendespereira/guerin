package br.com.guerin.Controller;

import br.com.guerin.Entity.Parameter;
import br.com.guerin.Entity.User;
import br.com.guerin.Service.IService.IParameterService;
import br.com.guerin.Service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
@RequestMapping("/api/parameter")
@RequiredArgsConstructor
public class ParameterController {
    private final IParameterService parameterService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Parameter parameter) {
        try {
            return ResponseEntity.ok().body(parameterService.save(parameter));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Parameter parameter) {
        try {
            return ResponseEntity.ok().body(parameterService.save(parameter));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(parameterService.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        parameterService.delete(id);
    }
    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(parameterService.findAll(pageable));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
