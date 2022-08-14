package br.com.guerin.controller;

import br.com.guerin.Entity.VaccineApplication;
import br.com.guerin.Service.IService.IVaccineApplicationService;
import br.com.guerin.Service.VaccineApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eduardo Mendes
 * @version 1.0.0
 * @since 1.0.0, 08/08/2022
 */

@Controller
@RequestMapping("/api/vaccineApplications")
public class VaccineApplicationController {

    @Autowired
    private IVaccineApplicationService vaccineApplicationService;

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
            return ResponseEntity.ok().body(this.vaccineApplicationService.findByVaccine(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll(
            Pageable pageable
    ) {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.findAll(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody VaccineApplication vaccineApplication) {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.save(vaccineApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idVaccineApplication}")
    public ResponseEntity<?> update(@RequestBody VaccineApplication vaccineApplication, @PathVariable Long idVaccineApplication) {
        try {
            return ResponseEntity.ok().body(this.vaccineApplicationService.update(idVaccineApplication, vaccineApplication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idVaccineApplication}")
    public ResponseEntity<?> updateStatus(
            @RequestBody VaccineApplication vaccineApplication,
            @PathVariable Long idVaccineApplication
    ) {
        try {
            this.vaccineApplicationService.disable(idVaccineApplication, vaccineApplication);
            return ResponseEntity.ok().body("Aplicação de vacina desativada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
