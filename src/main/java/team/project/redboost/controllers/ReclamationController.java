package team.project.redboost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.redboost.entities.Reclamation;
import team.project.redboost.services.ReclamationService;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    // Récupérer toutes les réclamations
    @GetMapping
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    // Récupérer une réclamation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.getReclamationById(id);
        return reclamation != null ? ResponseEntity.ok(reclamation) : ResponseEntity.notFound().build();
    }

    // Créer une nouvelle réclamation
    @PostMapping
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        return ResponseEntity.ok(reclamationService.createReclamation(reclamation));
    }

    // Supprimer une réclamation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        return reclamationService.deleteReclamation(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Mettre à jour une réclamation
    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(
            @PathVariable Long id,
            @RequestBody Reclamation updatedReclamation) {
        Reclamation updated = reclamationService.updateReclamation(id, updatedReclamation);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
