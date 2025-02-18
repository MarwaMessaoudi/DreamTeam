package team.project.redboost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.project.redboost.entities.Reclamation;
import team.project.redboost.repositories.ReclamationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    // ID utilisateur statique (par exemple, ID 1 pour un utilisateur spécifique)
    private static final Long USER_ID = 1L;

    // Récupérer toutes les réclamations
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    // Récupérer une réclamation par son ID
    public Reclamation getReclamationById(Long id) {
        Optional<Reclamation> reclamation = reclamationRepository.findById(id);
        return reclamation.orElse(null);  // Retourne null si non trouvé
    }

    // Créer une nouvelle réclamation
    public Reclamation createReclamation(Reclamation reclamation) {
        // Assigner l'ID utilisateur statique
        reclamation.setIdUtilisateur(USER_ID);  // Utiliser l'ID utilisateur fixe
        return reclamationRepository.save(reclamation);
    }

    // Mettre à jour une réclamation existante
    public Reclamation updateReclamation(Long id, Reclamation reclamation) {
        if (reclamationRepository.existsById(id)) {
            reclamation.setIdReclamation(id);
            return reclamationRepository.save(reclamation);
        }
        return null;  // Retourne null si la réclamation n'existe pas
    }

    // Supprimer une réclamation
    public boolean deleteReclamation(Long id) {
        if (reclamationRepository.existsById(id)) {
            reclamationRepository.deleteById(id);
            return true;
        }
        return false;  // Retourne false si la réclamation n'existe pas
    }
}
