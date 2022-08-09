package br.com.guerin.Controller;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Service.CattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/cattle")
public class CattleController {

    @Autowired
    private CattleService cattleService;

    @GetMapping("/{cattleId}")
    public ResponseEntity<?> findById(@PathVariable("cattleId") Long cattleId) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findById(cattleId).get());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findAll(pageable));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Cattle cattle) {
        try {
            this.cattleService.insert(cattle);
            return ResponseEntity.ok().body("Gado cadastrado com sucesso!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cattleId}")
    public ResponseEntity<?> update(@RequestBody Cattle cattle, @PathVariable Long cattleId) {
        try {
            this.cattleService.update(cattleId, cattle);
            return ResponseEntity.ok().body("Gado atualizado com sucesso!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/inactivate/{cattleId}")
    public ResponseEntity<?> inactivate(@RequestBody Cattle cattle, @PathVariable Long cattleId) {
        try {
            this.cattleService.inactivate(cattleId, cattle);
            return ResponseEntity.ok().body("Gado inativado com sucesso!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
