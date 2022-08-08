package br.com.guerin.controller;

import br.com.guerin.Entity.Vaccine;
import br.com.guerin.Service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/vaccines")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/{idVaccine}")
    public ResponseEntity<Vaccine> findById(
        @PathVariable("idVaccine") Long idVaccine
    ){
        return ResponseEntity.ok().body(this.vaccineService.findById(idVaccine).get());
    }

    @GetMapping
    public ResponseEntity<Page<Vaccine>> findAll(
            Pageable pageable
    ){
        return ResponseEntity.ok().body(this.vaccineService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody Vaccine vaccine
    ){
        try{
            this.vaccineService.insert(vaccine);
            return ResponseEntity.ok().body("Vacina cadastrada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idVaccine}")
    public ResponseEntity<?> update(
        @RequestBody Vaccine vaccine,
        @PathVariable Long idVaccine
    ){
        try{
            this.vaccineService.update(idVaccine, vaccine);
            return ResponseEntity.ok().body("Vacina editada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{idVaccine}")
    public ResponseEntity<?> disable(
            @RequestBody Vaccine vaccine,
            @PathVariable Long idVaccine
    ){
        try{
            this.vaccineService.disable(idVaccine, vaccine);
            return ResponseEntity.ok().body("Vacina desativada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
