package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface for user account repository actions
 */
@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
    Optional<Attendee> findByUsername(String username);
    Optional<Attendee> findByEmail(String email);
}
