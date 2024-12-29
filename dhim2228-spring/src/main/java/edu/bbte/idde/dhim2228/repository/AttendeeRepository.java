package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Attendee findByEventIdAndUserId(Long eventId, Long userId);

    List<Attendee> findAllByEventId(Long eventId);

    @Modifying
    @Query("DELETE FROM Attendee a WHERE a.event.id = :eventId AND a.user.id = :userId")
    @Transactional
    void deleteByEventIdAndUserId(Long eventId, Long userId);
}
