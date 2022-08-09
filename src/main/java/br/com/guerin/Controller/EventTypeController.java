package br.com.guerin.Controller;

import br.com.guerin.Entity.EventType;
import br.com.guerin.Entity.Specie;
import br.com.guerin.Service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/event_type")
public class EventTypeController {
    @Autowired
    private EventTypeService eventTypeService;

    @GetMapping("/{idEvent_type}")
    public ResponseEntity<EventType> findById(
            @PathVariable("idEvent_type") Long idEvent_type
    ){
        return ResponseEntity.ok().body(this.eventTypeService.findById(idEvent_type));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<EventType>> listByAllPage(
            Pageable pageable
    ){
        return  ResponseEntity.ok().body(this.eventTypeService.listAll(pageable));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestBody EventType eventType
    ){
        try {
            this.eventTypeService.save(eventType);
            return ResponseEntity.ok().body("Tipo de Evento Cadastrado com sucesso !");
        }catch (Exception e){
            return ResponseEntity.ok().body("Falha ao Cadastrar Tipo de Evento");
        }
    }

    @PutMapping("/{idEvent_type}")
    public ResponseEntity<?> update(
            @PathVariable Long idEvent_type,
            @RequestBody EventType eventType
    ){
        try {
            this.eventTypeService.update(idEvent_type, eventType);
            return  ResponseEntity.ok().body("Tipo de evento editado com sucesso !");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Não foi possivel editar o Tipo de evento");
        }
    }

    @PutMapping("/desativar/{idEvent_type}")
    public ResponseEntity<?> desativar(
            @PathVariable Long idEvent_type,
            @RequestBody EventType eventType
    ){
        try {
            this.eventTypeService.desativar(idEvent_type, eventType);
            return ResponseEntity.ok().body("Tipo de Evento desativado com sucesso");


        }catch (Exception e){
            return ResponseEntity.badRequest().body("Não foi possivel desativar o Tipo de Evento");
        }
    }
}
