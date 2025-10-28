package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;

import java.util.List;

public interface TicketService {
     /**
      * Create a ticket.
      * @param request The ticket information.
      * @return The created ticket.
      */
     Ticket createTicket(CreateUpdateTicketRequest request);

     /**
      * Sets a ticket status to cancel.
      * @param userId The id of the requesting user.
      * @param ticketId The ticket to be cancelled.
      * @param request Ticket information (event id)
      * @return The now cancelled ticket
      */
     Ticket cancelTicket(Integer userId, Integer ticketId, CreateUpdateTicketRequest request);

     /**
      * Cancel all tickets belonging to a user
      * @param userId The id of the user.
      * @return success
      */
     boolean cancelAllUser(Integer userId);/**

      * Cancel all tickets to an event
      * @param eventId The id of the event.
      * @return success
      */
     boolean cancelAllEvent(Integer eventId);

     /**
      * Gets all tickets belonging to a user.
      * @param userId The id of the user.
      * @return A list of tickets.
      */
     List<Ticket> getTicketByUserId(Integer userId);

     /**
      * Gets a ticket by its id
      * @param ticketId The ticket id.
      * @return The Ticket.
      */
     Ticket getTicketById(Integer ticketId);
}
