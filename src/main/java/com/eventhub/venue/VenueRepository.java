package com.eventhub.venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VenueRepository extends JpaRepository<Venue, UUID>
{
    List<Venue> findAllByActiveTrueOrderByCreatedAtDesc();

    boolean existsByNameIgnoreCaseAndCityIgnoreCaseAndActiveTrue(String name, String city);
}
