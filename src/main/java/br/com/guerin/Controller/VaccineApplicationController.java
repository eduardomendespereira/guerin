package br.com.guerin.Controller;

import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Service.IService.IVaccineApplicationService;
import br.com.guerin.Service.IService.IVaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eduardo Mendes
 * @version 1.0.0
 * @since 1.0.0, 08/08/2022
 */

@RestController
@RequestMapping("/api/vaccineApplications")
@RequiredArgsConstructor
public class VaccineApplicationController {
    private final IVaccineApplicationService vaccineApplicationService;
    private final IVaccineService vaccineService;

    @GetMapping("/{idVaccineApplication}")
    public ResponseEntity<?> findById(@PathVariable("idVaccineApplication") Long idVaccineApplication) {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.findById(idVaccineApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/vaccine/{id}")
    public ResponseEntity<?> findByVaccine(@PathVariable("id") Long id) {
        try {
            var vaccine = this.vaccineService.findById(id);
            if (vaccine.isPresent()) {
                return ResponseEntity.ok().body(this.vaccineApplicationService.findByVaccine(vaccine.get()));
            } else {
                return ResponseEntity.badRequest().body("Vacina não encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody VaccineApplication vaccineApplication) {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.save(vaccineApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idVaccineApplication}")
    public ResponseEntity<?> update( @PathVariable Long idVaccineApplication, @RequestBody VaccineApplication vaccineApplication) {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.update(idVaccineApplication, vaccineApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disable(@PathVariable("id") Long id) {

        try{
            vaccineApplicationService.disable(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enable(@PathVariable("id") Long id) {
        try {
            vaccineApplicationService.enable(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.count());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
