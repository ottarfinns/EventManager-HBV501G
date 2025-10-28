package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.EventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Request object for creating and updating Events.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateEventRequest {
    private String name;
    private EventType type;
    private String description;
    private LocalDateTime dateTime;
    private Integer maxPeople;
    private Double price;
    private Venue venue;
}
