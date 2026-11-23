package com.eventhub.event;

import com.eventhub.booking.BookingService;
import com.eventhub.event.dto.EventFormRequest;
import com.eventhub.event.exception.EventCapacityExceedsVenueException;
import com.eventhub.event.exception.VenueAlreadyBookedException;
import com.eventhub.venue.VenueService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/event-form")
    public ModelAndView getEventFormPage()
    {
        EventFormRequest eventForm = new EventFormRequest();

        ModelAndView modelAndView = new ModelAndView("event-form");
        modelAndView.addObject("eventForm", eventForm);
        modelAndView.addObject("venues", venueService.getAllVenues());
        return modelAndView;
    }

    @PostMapping("/events")
    public ModelAndView createEvent(@Valid @ModelAttribute("eventForm") EventFormRequest eventForm,
                                    BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors())
        {
            ModelAndView modelAndView = new ModelAndView("event-form");
            modelAndView.addObject("eventForm", eventForm);
            modelAndView.addObject("venues", venueService.getAllVenues());
            return modelAndView;
        }

        UUID organizerId = (UUID) httpSession.getAttribute("user_id");

        try
        {
            eventService.createEvent(eventForm, organizerId);

            redirectAttributes.addFlashAttribute("successMessage", "Event created successfully.");

            return new ModelAndView("redirect:/events");
        }
        catch(EventCapacityExceedsVenueException ex)
        {
            bindingResult.rejectValue("capacity", "event.capacity.exceedsVenue", ex.getMessage());
        }
        catch(VenueAlreadyBookedException ex)
        {
            bindingResult.rejectValue("venueId", "event.venue.alreadyBooked", ex.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView("event-form");
        modelAndView.addObject("eventForm", eventForm);
        modelAndView.addObject("venues", venueService.getAllVenues());
        return modelAndView;
    }
}
