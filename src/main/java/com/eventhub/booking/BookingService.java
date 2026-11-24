package com.eventhub.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public boolean hasConfirmedBooking(UUID userId, UUID eventId)
    {
        return bookingRepository.existsByUserIdAndEventIdAndStatus(userId, eventId, BookingStatus.CONFIRMED);
    }

    public boolean hasActiveBookings(UUID eventId)
    {
        return bookingRepository.existsByEventIdAndStatusNot(eventId, BookingStatus.CANCELLED);
    }

    @Transactional
    public void deleteAllBookingsForEvent(UUID eventId)
    {
        bookingRepository.deleteAllByEventId(eventId);
    }

    public BigDecimal getRevenueForOrganizer(UUID organizerId)
    {
        return bookingRepository.sumRevenueByOrganizerId(organizerId);
    }
}
