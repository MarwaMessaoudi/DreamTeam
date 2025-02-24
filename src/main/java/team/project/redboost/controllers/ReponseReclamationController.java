package team.project.redboost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.redboost.entities.ReponseReclamation;
import team.project.redboost.services.ReponseReclamationService;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations/{idReclamation}/reponses")
public class ReponseReclamationController {

    @Autowired
    private ReponseReclamationService reponseService;

    // Récupérer toutes les réponses pour une réclamation
    @GetMapping
    public List<ReponseReclamation> getReponsesByReclamation(@PathVariable Long idReclamation) {
        return reponseService.getReponsesByReclamation(idReclamation);
    }

    // Ajouter une nouvelle réponse
    @PostMapping
    public ResponseEntity<ReponseReclamation> createReponse(
            @PathVariable Long idReclamation,
            @RequestBody ReponseReclamation reponse) {
        ReponseReclamation saved = reponseService.createReponse(idReclamation, reponse);
        return saved != null ? ResponseEntity.ok(saved) : ResponseEntity.notFound().build();
    }

    // Mettre à jour une réponse existante
    @PutMapping("/{idReponse}")
    public ResponseEntity<ReponseReclamation> updateReponse(
            @PathVariable Long idReponse,
            @RequestBody ReponseReclamation updatedReponse) {
        ReponseReclamation updated = reponseService.updateReponse(idReponse, updatedReponse);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Supprimer une réponse
    @DeleteMapping("/{idReponse}")
    public ResponseEntity<Void> deleteReponse(@PathVariable Long idReponse) {
        return reponseService.deleteReponse(idReponse) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
