package com.eventhub.booking;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService
{
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository)
    {
        this.bookingRepository = bookingRepository;
    }

    public int getBookedSeatsForEvent(UUID eventId)
    {
        return bookingRepository.sumBookedSeatsByEventId(eventId);
    }
}
