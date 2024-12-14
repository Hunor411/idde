package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Collection<Event> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name, String location);

    Optional<Event> findFirstByDateAfterOrderByDateAsc(LocalDateTime date);
}
