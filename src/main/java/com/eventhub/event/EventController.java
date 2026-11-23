package com.eventhub.event;

import com.eventhub.booking.BookingService;
import com.eventhub.venue.VenueService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class EventController
{
    private final EventService eventService;
    private final VenueService venueService;
    private final BookingService bookingService;

    public EventController(EventService eventService, VenueService venueService, BookingService bookingService)
    {
        this.eventService = eventService;
        this.venueService = venueService;
        this.bookingService = bookingService;
    }

    @GetMapping("/events")
    public ModelAndView getEventsPage(@RequestParam(required = false) String search,
                                      @RequestParam(required = false) UUID venueId,
                                      HttpSession httpSession)
    {
        UUID currentUserId = (UUID) httpSession.getAttribute("user_id");
        String currentUserRole = (String) httpSession.getAttribute("user_role");

        List<Event> events = eventService.getVisibleEventsForUser(currentUserId, currentUserRole, search, venueId);

        Map<UUID, Integer> bookedSeatsByEvent = new HashMap<>();
        Map<UUID, Integer> availableSeatsByEvent = new HashMap<>();
        Map<UUID, Integer> percentBookedByEvent = new HashMap<>();

        for(Event event : events)
        {
            int bookedSeats = bookingService.getBookedSeatsForEvent(event.getId());
            int availableSeats = event.getCapacity() - bookedSeats;
            int percentBooked = event.getCapacity() == 0 ? 0 : (int) ((bookedSeats * 100.0) / event.getCapacity());

            bookedSeatsByEvent.put(event.getId(), bookedSeats);
            availableSeatsByEvent.put(event.getId(), availableSeats);
            percentBookedByEvent.put(event.getId(), percentBooked);
        }

        ModelAndView modelAndView = new ModelAndView("events");
        modelAndView.addObject("events", events);
        modelAndView.addObject("venues", venueService.getAllVenues());
        modelAndView.addObject("search", search);
        modelAndView.addObject("venueId", venueId);
        modelAndView.addObject("bookedSeatsByEvent", bookedSeatsByEvent);
        modelAndView.addObject("availableSeatsByEvent", availableSeatsByEvent);
        modelAndView.addObject("percentBookedByEvent", percentBookedByEvent);

        return modelAndView;
    }
}
