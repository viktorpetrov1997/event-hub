package com.eventhub.venue;

import com.eventhub.venue.dto.VenueFormRequest;
import com.eventhub.venue.exception.VenueAlreadyExistsException;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/venue-form")
    public ModelAndView getVenueFormPage()
    {
        VenueFormRequest venueForm = new VenueFormRequest();

        ModelAndView modelAndView = new ModelAndView("venue-form");
        modelAndView.addObject("venueForm", venueForm);
        return modelAndView;
    }

    @PostMapping("/venues")
    public ModelAndView createVenue(@Valid @ModelAttribute("venueForm") VenueFormRequest venueForm,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors())
        {
            ModelAndView modelAndView = new ModelAndView("venue-form");
            modelAndView.addObject("venueForm", venueForm);
            return modelAndView;
        }

        try
        {
            venueService.createVenue(venueForm);
        }
        catch(VenueAlreadyExistsException ex)
        {
            bindingResult.rejectValue("name", "venue.name.duplicate", ex.getMessage());

            ModelAndView modelAndView = new ModelAndView("venue-form");
            modelAndView.addObject("venueForm", venueForm);
            return modelAndView;
        }

        redirectAttributes.addFlashAttribute("successMessage", "Venue created successfully.");

        return new ModelAndView("redirect:/venues");
    }
}
