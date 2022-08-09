package br.com.guerin.Controller;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Service.CattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import springfox.documentation.service.Response;

@Controller
@RequestMapping("/api/cattle")
public class CattleController {

    @Autowired
    private CattleService cattleService;

    @GetMapping("/{cattleId}")
    public ResponseEntity<Cattle> findById(@PathVariable("cattleId") Long cattleId) {
        return ResponseEntity.ok().body(this.cattleService.findById(cattleId).get());
    }

    @GetMapping
    public ResponseEntity<Page<Cattle>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.cattleService.findAll(pageable));
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
