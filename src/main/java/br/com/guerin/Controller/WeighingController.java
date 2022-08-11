package br.com.guerin.Controller;

import br.com.guerin.Entity.Weighing;
import br.com.guerin.Service.WeighingService;
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
@RequestMapping("/api/weighing")
public class WeighingController {

    @Autowired
    private WeighingService weighingService;

    /**
     *
     * @param idWeighing
     * @return
     */
    @GetMapping("/{idWeighing}")
    public ResponseEntity<Weighing> findById(
            @PathVariable("idWeighing") Long idWeighing
    ){
        return ResponseEntity.ok().body(this.weighingService.findById(idWeighing));
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Weighing>> listByAllPage(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.weighingService.listAll(pageable));
    }

    /**
     *
     * @param weighing
     * @return
     */
    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody Weighing weighing
    ){
        try {
            this.weighingService.insert(weighing);
            return ResponseEntity.ok().body("Pesagem Cadastrada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     *
     * @param idWeighing
     * @param weighing
     * @return
     */
    @PutMapping("/{idWeighing}")
    public ResponseEntity<?> update(
            @PathVariable Long idWeighing,
            @RequestBody Weighing weighing
    ){
        try {
            this.weighingService.update(idWeighing, weighing);
            return ResponseEntity.ok().body("Pesagem Atualizada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     *
     * @param idWeighing
     * @param weighing
     * @return
     */
    @PutMapping("/desativar/{idWeighing}")
    public ResponseEntity<?> disable(
            @PathVariable Long idWeighing,
            @RequestBody Weighing weighing
    ){
        try {
            this.weighingService.disable(idWeighing, weighing);
            return ResponseEntity.ok().body("Pesagem Desativada com Sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


