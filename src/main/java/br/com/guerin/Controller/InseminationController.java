package br.com.guerin.Controller;

import br.com.guerin.Entity.Insemination;
import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Service.IService.IInseminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/inseminations")
@RequiredArgsConstructor
public class InseminationController {

    private final IInseminationService inseminationService;

    @GetMapping("/{idInsemination}")
    public ResponseEntity<?> findById(
            @PathVariable("idInsemination") Long idInsemination
    ) {
        try {
            return ResponseEntity.ok().body(this.inseminationService.findById(idInsemination));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(
    ) {
        try {
            return ResponseEntity.ok().body(this.inseminationService.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody Insemination insemination
    ) {
        try {
            return ResponseEntity.ok().body(this.inseminationService.save(insemination));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody Insemination insemination
    ) {
        try {
            return ResponseEntity.ok().body(this.inseminationService.update(insemination));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/disable/{id}")
    public void disable(@PathVariable("id") Long id) {
        this.inseminationService.disable(id);
    }

    @GetMapping("/enable/{id}")
    public void enable(@PathVariable("id") Long id) {
        this.inseminationService.enable(id);
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try {
            return ResponseEntity.ok().body(this.inseminationService.count());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
