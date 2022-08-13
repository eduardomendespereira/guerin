package br.com.guerin.Controller;

import br.com.guerin.Entity.Specie;
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
    private SpecieService specieService;


    @GetMapping("/{idSpecie}")
    public ResponseEntity<Specie> findById(
            @PathVariable("idSpecie") Long idSpecie
    ){
        return ResponseEntity.ok().body(this.specieService.findById(idSpecie));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Specie>> listByAllPage(
            Pageable pageable
    ){
        return  ResponseEntity.ok().body(this.specieService.listAll(pageable));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestBody Specie specie
    ){
        try {
            this.specieService.save(specie);
            return ResponseEntity.ok().body("Especie Cadastrada com sucesso !");
        }catch (Exception e){
            return ResponseEntity.ok().body("Falha ao Cadastrar Especie");
        }
    }
    
    @PutMapping("/{idSpecie}")
    public ResponseEntity<?> update(
            @PathVariable Long idSpecie,
            @RequestBody Specie specie
    ){
        try {
            this.specieService.update(idSpecie, specie);
            return  ResponseEntity.ok().body("Especie editada com sucesso !");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Não foi possivel editar a Especie");
        }
    }

    @PutMapping("/desativar/{idSpecie}")
    public ResponseEntity<?> desativar(
            @PathVariable Long idSpecie,
            @RequestBody Specie specie
    ){
        try {
            this.specieService.inactivate(idSpecie, specie);
            return ResponseEntity.ok().body("Especie desativada com sucesso");


        }catch (Exception e){
            return ResponseEntity.badRequest().body("Não foi possivel desativar a Especie");
        }
    }

}
