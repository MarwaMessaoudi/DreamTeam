package team.project.redboost.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.project.redboost.entities.RendezVous;
import team.project.redboost.repositories.RendezVousRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    // Créer un rendez-vous
    public RendezVous createRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    // Récupérer tous les rendez-vous
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    // Récupérer un rendez-vous par ID
    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    // Mettre à jour un rendez-vous
    public RendezVous updateRendezVous(Long id, RendezVous newRendezVous) {
        return rendezVousRepository.findById(id)
            .map(existingRdv -> {
                existingRdv.setNomComplet(newRendezVous.getNomComplet());
                existingRdv.setEmail(newRendezVous.getEmail());
                existingRdv.setDate(newRendezVous.getDate());
                existingRdv.setHeure(newRendezVous.getHeure());
                existingRdv.setMessage(newRendezVous.getMessage());
                return rendezVousRepository.save(existingRdv);
            })
            .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'id: " + id));
    }

    // Supprimer un rendez-vous
    public void deleteRendezVous(Long id) {
        if (!rendezVousRepository.existsById(id)) {
            throw new RuntimeException("Rendez-vous non trouvé avec l'id: " + id);
        }
        rendezVousRepository.deleteById(id);
    }
}
