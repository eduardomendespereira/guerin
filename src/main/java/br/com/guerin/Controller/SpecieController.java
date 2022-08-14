package br.com.guerin.Controller;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Service.IService.ISpecieService;
import br.com.guerin.Service.SpecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/species")
public class SpecieController {
    @Autowired
    private ISpecieService specieService;


    @GetMapping("/{idSpecie}")
    public ResponseEntity<?> findById(@PathVariable("idSpecie") Long idSpecie) {
        try {
            return ResponseEntity.ok().body(this.specieService.findById(idSpecie));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> listByAllPage(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(this.specieService.listAll(pageable));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Specie specie) {
        try {
            return ResponseEntity.ok().body(specieService.save(specie));
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

    @PutMapping("/desativar/{idSpecie}")
    public ResponseEntity<?> desativar(@PathVariable Long idSpecie, @RequestBody Specie specie) {
        try {
            this.specieService.desativar(idSpecie, specie);
            return ResponseEntity.ok().body("Especie desativada com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
