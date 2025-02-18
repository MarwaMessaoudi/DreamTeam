package team.project.redboost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.redboost.entities.RendezVous;


@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
