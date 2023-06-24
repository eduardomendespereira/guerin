package br.com.guerin.Controller;

import br.com.guerin.Entity.Cattle;
import br.com.guerin.Service.NotificationService;
import br.com.guerin.Service.IService.ICattleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import DTO.Notification.Notification;

import java.util.List;

import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/api/cattle")
@RequiredArgsConstructor
public class CattleController {
    private final ICattleService cattleService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(this.cattleService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/gender/female")
    public ResponseEntity<?> findByAllGenderFemale() {
        try {
            return ResponseEntity.ok().body(this.cattleService.findByAllGenderFemale());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{cattleId}")
    public ResponseEntity<?> findById(@PathVariable("cattleId") Long cattleId) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findById(cattleId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/earring/{earring}")
    public ResponseEntity<?> findByEarring(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findByEarring(earring));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/farm/{farm_id}")
    public ResponseEntity<?> findByFarm(@PathVariable Long farm_id) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findByFarm(farm_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/specie/{specie_id}")
    public ResponseEntity<?> findBySpecie(@PathVariable Long specie_id) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findBySpecie(specie_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/parents/{earring}")
    public ResponseEntity<?> findParents(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findParents(earring));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/children/{earring}")
    public ResponseEntity<?> findChildren(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.findChildren(earring));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Cattle cattle) {
        try {
            NotificationService notificationService = new NotificationService();
            var createdCattle = this.cattleService.save(cattle, notificationService);
            if (createdCattle != null) {
                return ResponseEntity.ok(cattle);
            } else if (notificationService.hasNotifications()) {
                List<Notification> notifications = notificationService.getNotifications();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notifications);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar o gado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disable/{earring}")
    public ResponseEntity<?> disable(@RequestBody Cattle cattle, @PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.disable(earring, cattle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/enable/{earring}")
    public ResponseEntity<?> enable(@RequestBody Cattle cattle, @PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.enable(earring, cattle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{earring}")
    public ResponseEntity<?> update(@RequestBody Cattle cattle, @PathVariable Long earring) {
        try {
            NotificationService notificationService = new NotificationService();
            var updatedCattle = this.cattleService.update(earring, cattle, notificationService);
            if (updatedCattle != null) {
                return ResponseEntity.ok(cattle);
            } else if (notificationService.hasNotifications()) {
                List<Notification> notifications = notificationService.getNotifications();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(notifications);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o gado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        try {
            return ResponseEntity.ok().body(this.cattleService.count());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/count/male")
    public ResponseEntity<?> countMale() {
        try {
            return ResponseEntity.ok().body(this.cattleService.countMale());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/count/female")
    public ResponseEntity<?> countFemale() {
        try {
            return ResponseEntity.ok().body(this.cattleService.countFemale());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/can-breed/{earring}")
    public ResponseEntity<?> canBreed(@PathVariable Long earring) {
        try {
            return ResponseEntity.ok().body(this.cattleService.canBreed(earring));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/lactating-cattles")
    public ResponseEntity<?> findLactatingCattles() {
        try {
            return ResponseEntity.ok().body(this.cattleService.findLactatingCattles());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
