package team.project.redboost.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.project.redboost.Models.RendezVous;
import team.project.redboost.Repository.RendezVousRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    public RendezVous createRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public RendezVous updateRendezVous(Long id, RendezVous newRendezVous) {
        return rendezVousRepository.findById(id).map(rendezVous -> {
            rendezVous.setNomComplet(newRendezVous.getNomComplet());
            rendezVous.setEmail(newRendezVous.getEmail());
            rendezVous.setMessage(newRendezVous.getMessage());
            rendezVous.setDate(newRendezVous.getDate());
            return rendezVousRepository.save(rendezVous);
        }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
    }

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }
}
