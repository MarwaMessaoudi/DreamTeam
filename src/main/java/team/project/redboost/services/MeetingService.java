package team.project.redboost.services;

import team.project.redboost.entities.Meeting;
import java.util.List;
import java.util.Optional;

public interface MeetingService {
    Meeting createMeeting(Meeting newMeeting);

    List<Meeting> getAllMeetings();

    Optional<Meeting> getMeetingById(Long id);

    Meeting updateMeeting(Long id, Meeting updatedMeeting);

    void deleteMeeting(Long id);
}