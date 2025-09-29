package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface for user account repository actions
 */
public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {
    Optional<Organizer> findByUsername(String username);
    Optional<Organizer> findByEmail(String email);
}
