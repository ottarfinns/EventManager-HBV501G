package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for ticket repository actions
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByAttendeeId(Integer attendeeId);
    List<Ticket> findByEventId(Integer eventId);
}
