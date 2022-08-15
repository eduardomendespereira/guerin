package br.com.guerin.Controller;


import br.com.guerin.Entity.Farm;
import br.com.guerin.Service.FarmService;
import br.com.guerin.Service.IService.IFarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/farm")
public class FarmController {

    @Autowired
    private IFarmService farmService;

    @GetMapping("/{farmId}")
    public ResponseEntity<?> findById(@PathVariable("farmId") Long farmId) {
        try {
            return ResponseEntity.ok().body(this.farmService.findById(farmId).get());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/byname/{farmName}")
    public ResponseEntity<?> findByName(@PathVariable("farmName") String farmName) {
        try {
            return ResponseEntity.ok().body(this.farmService.findByName(farmName));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok().body(this.farmService.findAll(pageable));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Farm farm) {
        try {
            return ResponseEntity.ok().body(this.farmService.save(farm));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{farmId}")
    public ResponseEntity<?> update(@RequestBody Farm farm, @PathVariable Long farmId) {
        try {
            return ResponseEntity.ok().body(this.farmService.update(farmId, farm));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/inactivate/{farmId}")
    public ResponseEntity<?> inactivate(@RequestBody Farm farm, @PathVariable Long farmId) {
        try {
            this.farmService.inactivate(farmId, farm);
            return ResponseEntity.ok().body("Fazenda inativada com sucesso!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
