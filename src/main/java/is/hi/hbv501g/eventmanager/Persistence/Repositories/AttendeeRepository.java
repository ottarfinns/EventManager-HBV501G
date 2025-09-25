package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for user account repository actions
 */
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

}