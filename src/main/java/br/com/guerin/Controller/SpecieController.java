package br.com.guerin.Controller;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Service.IService.ISpecieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpecieController {
    private final ISpecieService specieService;

    @GetMapping("/{idSpecie}")
    public ResponseEntity<?> findById(@PathVariable("idSpecie") Long idSpecie) {
        try {
            if(specieService.findById(idSpecie).isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.specieService.findById(idSpecie));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/name/{nameSpecie}")
    public ResponseEntity<?> findByName(@PathVariable("nameSpecie") String name){
        try {
            if(specieService.findByName(name).isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.specieService.findByName(name));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listByAllPage() {
        try {
            return ResponseEntity.ok().body(this.specieService.listAll());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Specie specie) {
        try {
            return ResponseEntity.ok().body(specieService.save(specie));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/enable/{idSpecie}")
    public ResponseEntity<?> enable(@PathVariable Long idSpecie) {
        try {
            this.specieService.enable(idSpecie);
            return ResponseEntity.ok().body("Especie ativada com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping("/disable/{idSpecie}")
    public ResponseEntity<?> disable(@PathVariable Long idSpecie, @RequestBody Specie specie) {
        try {
            this.specieService.disable(idSpecie, specie);
            return ResponseEntity.ok().body("Especie desativada com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping("/{idSpecie}")
    public ResponseEntity<?> update(@PathVariable Long idSpecie, @RequestBody Specie specie) {
        try {
            return ResponseEntity.ok().body(specieService.update(idSpecie, specie));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try {
            return ResponseEntity.ok().body(this.specieService.count());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
