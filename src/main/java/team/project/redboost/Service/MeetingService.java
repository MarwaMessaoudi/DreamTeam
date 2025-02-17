package team.project.redboost.Service;


import team.project.redboost.Models.Meeting;
import java.util.List;

public interface MeetingService {
    List<Meeting> getAllMeetings();
    Meeting getMeetingById(Long id);
    Meeting createMeeting(Meeting meeting);
    void deleteMeeting(Long id);
}

