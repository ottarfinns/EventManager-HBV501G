package is.hi.hbv501g.eventmanager.Requests;

import lombok.AllArgsConstructor;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket.Status;

@AllArgsConstructor
public class CreateUpdateTicketRequest {
    public Integer id;
    public Integer attendeeId;
    public Integer eventId;
    public Double price;
    public Status status;


    //Constructor for Create
    public CreateUpdateTicketRequest(Integer attendeeId, Integer eventId, Double price, Status status) {
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.price = price;
        this.status = status;
    }
}
