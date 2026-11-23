package com.eventhub.event;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class EventService
{
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    public List<Event> getVisibleEventsForUser(UUID currentUserId, String currentUserRole, String search, UUID venueId)
    {
        LocalDateTime now = LocalDateTime.now();

        List<Event> events = eventRepository.findAllByPublishedTrueAndEndTimeAfterOrderByStartTimeAsc(now);

        if("ORGANIZER".equals(currentUserRole) && currentUserId != null)
        {
            List<Event> organizerEvents = eventRepository.findAllByOrganizerIdAndEndTimeAfter(currentUserId, now);
            events = Stream.concat(events.stream(), organizerEvents.stream())
                    .distinct()
                    .sorted(Comparator.comparing(Event::getStartTime))
                    .toList();
        }

        if(search != null && !search.isBlank())
        {
            String keyword = search.toLowerCase();
            events = events.stream()
                    .filter(event ->
                            event.getTitle().toLowerCase().contains(keyword)
                                    || event.getDescription().toLowerCase().contains(keyword))
                    .toList();
        }

        if(venueId != null)
        {
            events = events.stream()
                    .filter(event -> event.getVenue() != null && event.getVenue().getId().equals(venueId))
                    .toList();
        }

        return events;
    }
}
