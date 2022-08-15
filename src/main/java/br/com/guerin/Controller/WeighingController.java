package br.com.guerin.Controller;

import br.com.guerin.Entity.Weighing;
import br.com.guerin.Service.IService.IWeighingService;
import br.com.guerin.Service.WeighingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class WeighingController {

    @Autowired
    private IWeighingService weighingService;

    /**
     * @param idWeighing
     * @return
     */
    @GetMapping("/{idWeighing}")
    public ResponseEntity<?> findById(@PathVariable("idWeighing") Long idWeighing) {
        try {
            return ResponseEntity.ok().body(this.weighingService.findById(idWeighing));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<?> listByAllPage(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(this.weighingService.listAll(pageable));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * @param weighing
     * @return
     */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Weighing weighing) {
        try {
            this.weighingService.save(weighing);
            return ResponseEntity.ok().body("Pesagem Cadastrada com Sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * @param idWeighing
     * @param weighing
     * @return
     */
    @PutMapping("/{idWeighing}")
    public ResponseEntity<?> update(@PathVariable Long idWeighing, @RequestBody Weighing weighing) {
        try {
            this.weighingService.update(idWeighing, weighing);
            return ResponseEntity.ok().body("Pesagem Atualizada com Sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     /**
     * @param idWeighing
     * @param weighing
     * @return
     */
    @PutMapping("/desativar/{idWeighing}")
    public ResponseEntity<?> disable(@PathVariable Long idWeighing, @RequestBody Weighing weighing) {
        try {
            this.weighingService.disable(idWeighing, weighing);
            return ResponseEntity.ok().body("Pesagem Desativada com Sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}