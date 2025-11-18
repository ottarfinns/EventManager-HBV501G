package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for event repository actions
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByOrganizerId(Integer organizerId);
}
