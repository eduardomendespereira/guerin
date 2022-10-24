package br.com.guerin.Controller;

import br.com.guerin.Entity.CattleEvent;
import br.com.guerin.Service.IService.ICattleEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/cattleEvent")
@RequiredArgsConstructor
public class CattleEventController {
    private final ICattleEventService cattleEventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(this.cattleEventService.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/disable/{id}")
    public void disable(@PathVariable("id") Long id) {
        cattleEventService.disable(id);
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CattleEvent cattleEvent){
        try {
            return ResponseEntity.ok().body(cattleEventService.save(cattleEvent));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(cattleEventService.findAll(PageRequest.of(0, 100)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/eventType/{id}")
    public ResponseEntity<?> findByEventType(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(cattleEventService.findByEventType(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/weighing/{id}")
    public ResponseEntity<?> findByWeighing(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(cattleEventService.findByWeighing(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/vacineApp/{id}")
    public ResponseEntity<?> findByVaccineApp(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(cattleEventService.findByVaccineApp(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/cattle/{id}")
    public ResponseEntity<?> findByCattle(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(cattleEventService.findByCattle(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody CattleEvent cattleEvent
    ) {
        try {
            return ResponseEntity.ok().body(this.cattleEventService.update(cattleEvent.getId(), cattleEvent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try {
            return ResponseEntity.ok().body(this.cattleEventService.count());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
