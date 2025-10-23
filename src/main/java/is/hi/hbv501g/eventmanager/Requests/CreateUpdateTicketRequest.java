package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CreateUpdateTicketRequest {
    private Integer id;
    private Integer attendeeId;
    private Integer eventId;
    private Double price;
    private Status status;


    //Constructor for Create
    public CreateUpdateTicketRequest(Integer eventId) {
        this.eventId = eventId;
    }

    public CreateUpdateTicketRequest(Status status) {
        this.status = status;
    }

}
