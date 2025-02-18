package team.project.redboost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.redboost.entities.ReponseReclamation;
import team.project.redboost.services.ReponseReclamationService;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations/{idReclamation}/reponses")
public class ReponseReclamationController {

    @Autowired
    private ReponseReclamationService reponseReclamationService;

    // Récupérer toutes les réponses d'une réclamation donnée
    @GetMapping
    public List<ReponseReclamation> getReponsesByReclamation(@PathVariable Long idReclamation) {
        return reponseReclamationService.getReponsesByReclamation(idReclamation);
    }

    // Récupérer une réponse par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ReponseReclamation> getReponseById(@PathVariable Long id) {
        ReponseReclamation reponse = reponseReclamationService.getReponseById(id);
        if (reponse != null) {
            return ResponseEntity.ok(reponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Créer une nouvelle réponse pour une réclamation donnée
    @PostMapping("/createReponse")
    public ResponseEntity<ReponseReclamation> createReponse(@PathVariable Long idReclamation, @RequestBody ReponseReclamation reponseReclamation) {
        ReponseReclamation savedReponse = reponseReclamationService.createReponse(idReclamation, reponseReclamation);
        if (savedReponse != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Mettre à jour une réponse existante
    @PutMapping("updateReponse/{id}")
    public ResponseEntity<ReponseReclamation> updateReponse(@PathVariable Long id, @RequestBody ReponseReclamation reponseReclamation) {
        ReponseReclamation updatedReponse = reponseReclamationService.updateReponse(id, reponseReclamation);
        if (updatedReponse != null) {
            return ResponseEntity.ok(updatedReponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Supprimer une réponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReponse(@PathVariable Long id) {
        if (reponseReclamationService.deleteReponse(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
