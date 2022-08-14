package br.com.guerin.Controller;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Service.CattleService;
import br.com.guerin.Service.IService.ICattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/cattle")
@RequiredArgsConstructor
public class CattleController {
    private final ICattleService cattleService;
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
    public ResponseEntity<?> save(@RequestBody Cattle cattle) {
        try {
            this.cattleService.save(cattle);
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
    @GetMapping("/earring/{earring}")
    public ResponseEntity<?> findByEarring(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findByEarring(earring));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/farm/{farm_id}")
    public ResponseEntity<?> findByFarm(@PathVariable Long farm_id) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findByFarm(farm_id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/specie/{specie_id}")
    public ResponseEntity<?> findBySpecie(@PathVariable Long specie_id) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findBySpecie(specie_id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/fathers/{earring}")
    public ResponseEntity<?> findFathers(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.getFathers(earring));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/sons/{earring}")
    public ResponseEntity<?> findSons(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.getSons(earring));
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
