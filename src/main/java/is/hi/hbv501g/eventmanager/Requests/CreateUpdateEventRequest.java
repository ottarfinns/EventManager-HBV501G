package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Event.eventType;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CreateUpdateEventRequest {
    public String name;
    public eventType type;
    public String description;
    public LocalDateTime dateTime;
    public Integer maxPeople;
    public Double price;
    public Venue venue;
}
