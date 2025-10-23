package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;

import java.util.List;

public interface TicketService {
     Ticket createTicket(CreateUpdateTicketRequest request);
     Ticket cancelTicket(Integer userId, Integer ticketId, CreateUpdateTicketRequest request);
     boolean cancelAllUser(Integer userId);
     boolean cancelAllEvent(Integer eventId);
     List<Ticket> getTicketByUserId(Integer userId);
     Ticket getTicketById(Integer ticketId);
}
