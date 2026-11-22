package com.eventhub.venue;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService
{
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository)
    {
        this.venueRepository = venueRepository;
    }

    public List<Venue> getAllVenues()
    {
        return venueRepository.findAllByActiveTrueOrderByCreatedAtDesc();
    }
}
