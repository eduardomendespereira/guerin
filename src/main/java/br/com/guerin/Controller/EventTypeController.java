package br.com.guerin.Controller;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Service.IService.IEventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/event_type")
@RequiredArgsConstructor
public class EventTypeController {
    private final IEventTypeService eventTypeService;

    @GetMapping("/{idEvent_type}")
    public ResponseEntity<?> findById(@PathVariable("idEvent_type") Long idEvent_type){
        try {
            return ResponseEntity.ok().body(this.eventTypeService.findById(idEvent_type));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listByAllPage(Pageable pageable){
        try {
            return ResponseEntity.ok().body(this.eventTypeService.listAll(pageable));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody EventType eventType){
        try {
            return ResponseEntity.ok().body(this.eventTypeService.save(eventType));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{idEvent_type}")
    public ResponseEntity<?> update(@PathVariable Long idEvent_type, @RequestBody EventType eventType){
        try {
            return ResponseEntity.ok().body(this.eventTypeService.update(idEvent_type, eventType));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/disable/{idEvent_type}")
    public ResponseEntity<?> disable(@PathVariable Long idEvent_type, @RequestBody EventType eventType){
        try {
            this.eventTypeService.disable(idEvent_type, eventType);
            return ResponseEntity.ok().body("Tipo de Evento desativado com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
