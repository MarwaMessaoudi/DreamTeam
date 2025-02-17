package team.project.redboost.Service;


import org.springframework.stereotype.Service;
import team.project.redboost.Models.Event;
import team.project.redboost.Repository.EventRepository;
import team.project.redboost.Service.EventService;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event updatedEvent) {
        Event existingEvent = getEventById(id);
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setStartTime(updatedEvent.getStartTime());
        existingEvent.setEndTime(updatedEvent.getEndTime());
        existingEvent.setEventIdGoogle(updatedEvent.getEventIdGoogle());
        existingEvent.setMeeting(updatedEvent.getMeeting());
        return eventRepository.save(existingEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
