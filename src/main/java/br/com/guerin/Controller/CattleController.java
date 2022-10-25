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

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(this.cattleService.findAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{cattleId}")
    public ResponseEntity<?> findById(@PathVariable("cattleId") Long cattleId) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findById(cattleId));
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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Cattle cattle) {
        try {
            return ResponseEntity.ok().body(this.cattleService.save(cattle));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/disable/{earring}")
    public ResponseEntity<?> disable(@RequestBody Cattle cattle, @PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body( this.cattleService.disable(earring, cattle));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{earring}")
    public ResponseEntity<?> update(@RequestBody Cattle cattle, @PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.update(earring, cattle));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try {
            return ResponseEntity.ok().body(this.cattleService.count());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/count/male")
    public ResponseEntity<?> countMale(){
        try {
            return ResponseEntity.ok().body(this.cattleService.countMale());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/count/female")
    public ResponseEntity<?> countFemale(){
        try {
            return ResponseEntity.ok().body(this.cattleService.countFemale());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
