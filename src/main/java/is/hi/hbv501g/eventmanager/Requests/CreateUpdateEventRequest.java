package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CreateUpdateEventRequest {
    public Integer id; //only for update
    public String name;
    public String type;
    public String description;
    public LocalDateTime dateTime;
    public int maxPeople;
    public Double price;
    public Venue venue;
    public Organizer organizer;
    public boolean isActive;

    //Constructor for Create
    public CreateUpdateEventRequest(
            String name,
            String type,
            String description, //optional
            LocalDateTime dateTime,
            int maxPeople,
            Double price,
            Venue venue,
            Organizer organizer,
            boolean isActive
    ) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateTime = dateTime;
        this.maxPeople = maxPeople;
        this.price = price;
        this.venue = venue;
        this.organizer = organizer;
        this.isActive = isActive;
    }
}
