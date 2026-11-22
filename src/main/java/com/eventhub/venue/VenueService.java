package com.eventhub.venue;

import com.eventhub.venue.dto.VenueFormRequest;
import com.eventhub.venue.exception.VenueAlreadyExistsException;
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

    public void createVenue(VenueFormRequest request)
    {
        if(venueRepository.existsByNameIgnoreCaseAndCityIgnoreCaseAndActiveTrue(request.getName(), request.getCity()))
        {
            throw new VenueAlreadyExistsException("A venue with this name already exists in this city.");
        }

        Venue venue = Venue.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .capacity(request.getCapacity())
                .build();

        venueRepository.save(venue);
    }
}
