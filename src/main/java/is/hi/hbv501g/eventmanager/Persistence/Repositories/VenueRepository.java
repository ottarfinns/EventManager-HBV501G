package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for event repository actions
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
