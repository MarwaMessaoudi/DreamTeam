package team.project.redboost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.redboost.entities.ReponseReclamation;

import java.util.List;

public interface ReponseReclamationRepository extends JpaRepository<ReponseReclamation, Long> {
    List<ReponseReclamation> findByReclamation_IdReclamation(Long idReclamation);
}
