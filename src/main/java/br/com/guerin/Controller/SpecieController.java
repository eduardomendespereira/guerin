package br.com.guerin.Controller;

import br.com.guerin.Entity.Specie;
import br.com.guerin.Service.IService.ISpecieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
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

    @PutMapping("/disable/{idSpecie}")
    public ResponseEntity<?> disable(@PathVariable Long idSpecie, @RequestBody Specie specie) {
        try {
            this.specieService.disable(idSpecie, specie);
            return ResponseEntity.ok().body("Especie desativada com sucesso");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
