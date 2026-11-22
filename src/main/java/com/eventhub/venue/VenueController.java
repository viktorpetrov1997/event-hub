package com.eventhub.venue;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VenueController
{
    private final VenueService venueService;

    public VenueController(VenueService venueService)
    {
        this.venueService = venueService;
    }

    @GetMapping("/venues")
    public ModelAndView getVenuesPage()
    {
        ModelAndView modelAndView = new ModelAndView("venues");
        modelAndView.addObject("venues", venueService.getAllVenues());
        return modelAndView;
    }
}
