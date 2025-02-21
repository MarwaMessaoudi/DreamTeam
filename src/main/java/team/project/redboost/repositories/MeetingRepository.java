package team.project.redboost.repositories;

import team.project.redboost.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByTitle(String title);

    List<Meeting> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Meeting> findByHostId(Long hostId);
}