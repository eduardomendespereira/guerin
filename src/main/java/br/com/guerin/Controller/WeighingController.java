package br.com.guerin.Controller;

import br.com.guerin.Entity.Weighing;
import br.com.guerin.Service.IService.IWeighingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gabriel Luiz C
 * @version 1.0.0
 * @since 1.0.0, 08/08/2022
 */

@Controller
@RequestMapping("/api/weighing")
@RequiredArgsConstructor
public class WeighingController {

    private final IWeighingService weighingService;


    @GetMapping("/media/{idWeighing}")
    public  ResponseEntity<?> weighingEarnByDay(@PathVariable("idWeighing") Long idWeighing) {
        try {
            return ResponseEntity.ok().body(this.weighingService.weighingEarnByDay(idWeighing));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{idWeighing}")
    public ResponseEntity<?> findById(@PathVariable("idWeighing") Long idWeighing) {
        try {
            return ResponseEntity.ok().body(this.weighingService.findById(idWeighing));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listByAllPage(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(this.weighingService.listAll(pageable));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Weighing weighing) {
        try {
            return ResponseEntity.ok().body(this.weighingService.save(weighing));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idWeighing}")
    public ResponseEntity<?> update(@PathVariable Long idWeighing, @RequestBody Weighing weighing) {
        try {
            return ResponseEntity.ok().body(this.weighingService.update(idWeighing, weighing));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idWeighing}")
    public ResponseEntity<?> disable(@PathVariable Long idWeighing) {
        try {
            this.weighingService.disable(idWeighing);
            return ResponseEntity.ok().body("Pesagem Desativada com Sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/enable/{idWeighing}")
    public ResponseEntity<?> enable(@PathVariable Long idWeighing) {
        try {
            this.weighingService.enable(idWeighing);
            return ResponseEntity.ok().body("Pesagem Ativada com Sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try {
            return ResponseEntity.ok().body(this.weighingService.count());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}