package team.project.redboost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.project.redboost.entities.Reclamation;
import team.project.redboost.entities.ReponseReclamation;
import team.project.redboost.repositories.ReponseReclamationRepository;
import team.project.redboost.services.ReclamationService;

import java.util.List;
import java.util.Optional;

@Service
public class ReponseReclamationService {

    @Autowired
    private ReponseReclamationRepository reponseReclamationRepository;

    @Autowired
    private ReclamationService reclamationService;

    // Get responses by reclamation ID
    public List<ReponseReclamation> getReponsesByReclamation(Long idReclamation) {
        return reponseReclamationRepository.findByReclamation_IdReclamation(idReclamation);
    }

    // Get a response by its ID
    public ReponseReclamation getReponseById(Long id) {
        Optional<ReponseReclamation> reponse = reponseReclamationRepository.findById(id);
        return reponse.orElse(null);  // Returns null if not found
    }

    // Create a new response for a reclamation
    public ReponseReclamation createReponse(Long reclamationId, ReponseReclamation reponseReclamation) {
        Reclamation reclamation = reclamationService.getReclamationById(reclamationId);
        if (reclamation != null) {
            reponseReclamation.setReclamation(reclamation);
            return reponseReclamationRepository.save(reponseReclamation);
        }
        return null;  // Returns null if the reclamation doesn't exist
    }

    // Update an existing response
    public ReponseReclamation updateReponse(Long id, ReponseReclamation reponseReclamation) {
        if (reponseReclamationRepository.existsById(id)) {
            return reponseReclamationRepository.save(reponseReclamation);
        }
        return null;  // Returns null if the response doesn't exist
    }

    // Delete a response
    public boolean deleteReponse(Long id) {
        if (reponseReclamationRepository.existsById(id)) {
            reponseReclamationRepository.deleteById(id);
            return true;
        }
        return false;  // Returns false if the response doesn't exist
    }
}
