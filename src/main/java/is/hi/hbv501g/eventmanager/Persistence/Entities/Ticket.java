package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity for Tickets, associations between Events and Attendees.
 */
@Entity
@Getter
@Setter
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@ManyToOne(fetch = FetchType.LAZY)
    private Integer attendeeId;
    //@ManyToOne(fetch=FetchType.LAZY)
    private Integer eventId;
    private Double price;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Ticket(Integer attendeeId, Integer eventId, Double price, Status status) {
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.price = price;
        this.status = status;
    }

    public enum Status {
        active,
        cancelled,
        checked_in,
        expired
    }

    public Ticket(Integer attendeeId, Double price, Status status) {
        this.attendeeId = attendeeId;
        this.price = price;
        this.status = status;
    }
}
