package br.com.guerin.Controller;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Service.IService.ICattleService;
import lombok.RequiredArgsConstructor;
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
            return ResponseEntity.ok().body(this.cattleService.findById(cattleId));
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
            return ResponseEntity.ok().body(this.cattleService.save(cattle));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cattleId}")
    public ResponseEntity<?> update(@RequestBody Cattle cattle, @PathVariable Long cattleId) {
        try {
            return ResponseEntity.ok().body(this.cattleService.update(cattleId, cattle));
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
    @GetMapping("/parents/{earring}")
    public ResponseEntity<?> findParents(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findParents(earring));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/children/{earring}")
    public ResponseEntity<?> findChildren(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findChildren(earring));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/disable/{cattleId}")
    public ResponseEntity<?> disable(@RequestBody Cattle cattle, @PathVariable Long cattleId) {
        try {
            this.cattleService.disable(cattleId, cattle);
            return ResponseEntity.ok().body("Gado inativado com sucesso!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
