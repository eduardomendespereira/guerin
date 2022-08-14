package br.com.guerin.controller;

import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Service.VaccineApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eduardo Mendes
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
 */

@Controller
@RequestMapping("/api/vaccineApplications")
public class VaccineApplicationController {

    @Autowired
    private VaccineApplicationService vaccineApplicationService;

    @GetMapping("/{idVaccineApplication}")
    public ResponseEntity<VaccineApplication> findById(
            @PathVariable("idVaccineApplication") Long idVaccineApplication
    ){
        return ResponseEntity.ok().body(this.vaccineApplicationService.findById(idVaccineApplication).get());
    }

    @GetMapping
    public ResponseEntity<Page<VaccineApplication>> findAll(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.vaccineApplicationService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody VaccineApplication vaccineApplication
    ){
        try {
            this.vaccineApplicationService.insert(vaccineApplication);
            return ResponseEntity.ok().body("Aplicação de vacina cadastrada com sucesso!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idVaccineApplication}")
    public ResponseEntity<?> update(
            @RequestBody VaccineApplication vaccineApplication,
            @PathVariable Long idVaccineApplication
    ){
        try {
            this.vaccineApplicationService.update(idVaccineApplication, vaccineApplication);
            return ResponseEntity.ok().body("Aplicação de vacina atualizada com sucesso!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idVaccineApplication}")
    public ResponseEntity<?> updateStatus(
            @RequestBody VaccineApplication vaccineApplication,
            @PathVariable Long idVaccineApplication
    ){
        try {
            this.vaccineApplicationService.disable(idVaccineApplication, vaccineApplication);
            return ResponseEntity.ok().body("Aplicação de vacina desativada com sucesso!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
