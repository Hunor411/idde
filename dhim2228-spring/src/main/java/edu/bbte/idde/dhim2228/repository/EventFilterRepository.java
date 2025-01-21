package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface EventFilterRepository extends EventRepository, JpaRepository<Event, Long> {
    Collection<Event> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name, String location);

    Optional<Event> findFirstByDateAfterOrderByDateAsc(LocalDateTime date);
}
