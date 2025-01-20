package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
