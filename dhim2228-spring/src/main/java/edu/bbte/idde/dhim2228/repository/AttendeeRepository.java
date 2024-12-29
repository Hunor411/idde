package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Attendee;
import edu.bbte.idde.dhim2228.model.AttendeeId;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, AttendeeId> {
    Collection<Attendee> findAllByEventId(Long eventId);

    @Modifying
    @Query("DELETE FROM Attendee a WHERE a.id = :id")
    void deleteById(@NonNull AttendeeId id);
}
