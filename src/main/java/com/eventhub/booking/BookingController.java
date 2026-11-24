package com.eventhub.booking;

import com.eventhub.event.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class BookingController
{
    private final BookingService bookingService;

    public BookingController(BookingService bookingService)
    {
        this.bookingService = bookingService;
    }

    @GetMapping("/my-bookings")
    public ModelAndView getMyBookings(HttpSession httpSession)
    {
        UUID userId = (UUID) httpSession.getAttribute("user_id");

        List<Booking> bookings = bookingService.getBookingsForUser(userId);

        ModelAndView modelAndView = new ModelAndView("my-bookings");
        modelAndView.addObject("bookings", bookings);
        return modelAndView;
    }
}
