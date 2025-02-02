package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Event;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Override
    @Modifying
    @Transactional
    @Query("DELETE FROM Event e WHERE e = :event")
    void delete(@NonNull Event event);
}
