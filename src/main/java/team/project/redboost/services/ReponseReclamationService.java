package team.project.redboost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.project.redboost.entities.Reclamation;
import team.project.redboost.entities.ReponseReclamation;
import team.project.redboost.repositories.ReponseReclamationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReponseReclamationService {

    @Autowired
    private ReponseReclamationRepository reponseReclamationRepository;

    @Autowired
    private ReclamationService reclamationService;

    // Récupérer toutes les réponses pour une réclamation donnée
    public List<ReponseReclamation> getReponsesByReclamation(Long idReclamation) {
        return reponseReclamationRepository.findByReclamation_IdReclamation(idReclamation);
    }

    // Créer une nouvelle réponse pour une réclamation
    public ReponseReclamation createReponse(Long reclamationId, ReponseReclamation reponse) {
        Reclamation reclamation = reclamationService.getReclamationById(reclamationId);
        if (reclamation != null) {
            reponse.setReclamation(reclamation);
            reponse.setDateReponse(new Date());
            return reponseReclamationRepository.save(reponse);
        }
        return null;
    }

    // Supprimer une réponse
    public boolean deleteReponse(Long id) {
        if (reponseReclamationRepository.existsById(id)) {
            reponseReclamationRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // Mettre à jour une réponse existante
    public ReponseReclamation updateReponse(Long idReponse, ReponseReclamation updatedReponse) {
        return reponseReclamationRepository.findById(idReponse).map(existingReponse -> {
            existingReponse.setMessage(updatedReponse.getMessage());
            existingReponse.setDateReponse(new Date());  // Met à jour la date
            return reponseReclamationRepository.save(existingReponse);
        }).orElse(null);
    }

}
