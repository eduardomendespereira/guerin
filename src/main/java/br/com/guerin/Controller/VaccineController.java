package br.com.guerin.controller;

import br.com.guerin.Entity.Vaccine;
<<<<<<< HEAD
=======
import br.com.guerin.Service.IService.IVaccineService;
>>>>>>> main
import br.com.guerin.Service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eduardo Mendes
<<<<<<< HEAD
 *
 * @since 1.0.0, 08/08/2022
 * @version 1.0.0
=======
 * @version 1.0.0
 * @since 1.0.0, 08/08/2022
>>>>>>> main
 */
@Controller
@RequestMapping("/api/vaccines")
public class VaccineController {

    @Autowired
<<<<<<< HEAD
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
=======
    private IVaccineService vaccineService;

    @GetMapping("/{idVaccine}")
    public ResponseEntity<?> findById(
            @PathVariable("idVaccine") Long idVaccine
    ) {
        try {
            return ResponseEntity.ok().body(this.vaccineService.findById(idVaccine));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            Pageable pageable
    ) {
        try {
            return ResponseEntity.ok().body(this.vaccineService.findAll(pageable));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
>>>>>>> main
    }

    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody Vaccine vaccine
<<<<<<< HEAD
    ){
        try{
            this.vaccineService.insert(vaccine);
            return ResponseEntity.ok().body("Vacina cadastrada com sucesso!");
        }catch (Exception e){
=======
    ) {
        try {
            this.vaccineService.save(vaccine);
            return ResponseEntity.ok().body("Vacina cadastrada com sucesso!");
        } catch (Exception e) {
>>>>>>> main
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idVaccine}")
    public ResponseEntity<?> update(
<<<<<<< HEAD
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
=======
            @RequestBody Vaccine vaccine,
            @PathVariable Long idVaccine
    ) {
        try {
            this.vaccineService.update(idVaccine, vaccine);
            return ResponseEntity.ok().body("Vacina editada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

>>>>>>> main
    @PutMapping("/disable/{idVaccine}")
    public ResponseEntity<?> disable(
            @RequestBody Vaccine vaccine,
            @PathVariable Long idVaccine
<<<<<<< HEAD
    ){
        try{
            this.vaccineService.disable(idVaccine, vaccine);
            return ResponseEntity.ok().body("Vacina desativada com sucesso!");
        }catch (Exception e){
=======
    ) {
        try {
            this.vaccineService.disable(idVaccine, vaccine);
            return ResponseEntity.ok().body("Vacina desativada com sucesso!");
        } catch (Exception e) {
>>>>>>> main
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
