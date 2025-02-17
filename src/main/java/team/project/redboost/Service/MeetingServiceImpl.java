package team.project.redboost.Service;


import team.project.redboost.Models.Meeting;
import team.project.redboost.Repository.MeetingRepository;
import team.project.redboost.Service.MeetingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingServiceImpl(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public Meeting getMeetingById(Long id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réunion non trouvée"));
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }
}
