package br.com.guerin.Controller;

import br.com.guerin.Service.IService.ICattleEventService;
import br.com.guerin.Service.IService.ICattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @DeleteMapping("/disable/{id}")
    public void disable(@PathVariable("id") Long id) {
        cattleEventService.disable(id);
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
}
