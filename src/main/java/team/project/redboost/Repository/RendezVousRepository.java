package team.project.redboost.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.redboost.Models.RendezVous;


@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
