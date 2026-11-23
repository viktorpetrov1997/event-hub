package com.eventhub.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID>
{
    @Query("""
        SELECT COALESCE(MAX(e.capacity), 0) FROM Event e
        WHERE e.venue.id = :venueId
          AND e.endTime > :now""")
    int findMaxUpcomingEventCapacityAtVenue(@Param("venueId") UUID venueId,
                                            @Param("now") LocalDateTime now);

    boolean existsByVenueIdAndEndTimeAfter(UUID venueId, LocalDateTime now);

    boolean existsByVenueId(UUID venueId);
}
