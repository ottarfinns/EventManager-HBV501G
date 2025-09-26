package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for event repository actions
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
