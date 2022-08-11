package br.com.guerin.Controller;

import br.com.guerin.Entity.EventTimeLine;
import br.com.guerin.Service.EventTLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gabriel Luiz C
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */

@Controller
@RequestMapping("/api/eventtl")
public class EventTLController {

    @Autowired
    private EventTLService eventService;

    /**
     *
     * @param idEventTL
     * @return
     */
    @GetMapping("/{idEventTL}")
    public ResponseEntity<EventTimeLine> findById(
            @PathVariable("idEventTL") Long idEventTL
    ){
        return ResponseEntity.ok().body(this.eventService.findById(idEventTL));
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<EventTimeLine>> listByAllPage(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.eventService.listAll(pageable));
    }

    /**
     *
     * @param eventTimeLine
     * @return
     */
    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody EventTimeLine eventTimeLine
    ){
        try {
            this.eventService.insert(eventTimeLine);
            return ResponseEntity.ok().body("Timeline Cadastrada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     *
     * @param idEventTL
     * @param eventTimeLine
     * @return
     */
    @PutMapping("/{idEventTL}")
    public ResponseEntity<?> update(
            @PathVariable Long idEventTL,
            @RequestBody EventTimeLine eventTimeLine
    ){
        try {
            this.eventService.update(idEventTL, eventTimeLine);
            return ResponseEntity.ok().body("Timeline Atualizada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     *
     * @param idEventTL
     * @param eventTimeLine
     * @return
     */
    @PutMapping("/desativar/{idEventTL}")
    public ResponseEntity<?> disable(
            @PathVariable Long idEventTL,
            @RequestBody EventTimeLine eventTimeLine
    ){
        try {
            this.eventService.disable(idEventTL, eventTimeLine);
            return ResponseEntity.ok().body("Timeline Desativada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


