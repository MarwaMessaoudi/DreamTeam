package team.project.redboost.repositories;

import team.project.redboost.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {
    Optional<user> findByUsername(String username);
    Optional<user> findByEmail(String email);
}
