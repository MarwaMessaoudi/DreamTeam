package team.project.redboost.Service;


import team.project.redboost.Models.Event;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
}
