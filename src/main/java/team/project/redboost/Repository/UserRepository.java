package team.project.redboost.Repository;

import team.project.redboost.Models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {
    Optional<user> findByEmail(String email);
}

