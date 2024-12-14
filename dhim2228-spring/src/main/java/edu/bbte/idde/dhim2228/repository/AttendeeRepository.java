package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
