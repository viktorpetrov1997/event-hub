package com.eventhub.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>
{
    @Query("""
        SELECT COALESCE(SUM(b.seats), 0) FROM Booking b
        WHERE b.event.id = :eventId
      AND b.status <> com.eventhub.booking.BookingStatus.CANCELLED""")
    int sumBookedSeatsByEventId(@Param("eventId") UUID eventId);

    boolean existsByUserIdAndEventIdAndStatus(UUID userId, UUID eventId, BookingStatus status);

    boolean existsByEventIdAndStatusNot(UUID eventId, BookingStatus status);
}