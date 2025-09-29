package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter @Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private @NonNull String name;
    private String type;
    private String description;
    private LocalDateTime dateTime;
    private int maxPeople;
    private @NonNull Double price;
    @ManyToOne(fetch = FetchType.LAZY)
    private @NonNull Venue venue;
    @ManyToOne(fetch = FetchType.LAZY)
    private Organizer organizer;
    private boolean isActive;

    public Event(
            String name,
            String type,
            String description,
            LocalDateTime dateTime,
            int maxPeople,
            Double price,
            Venue venue,
            Organizer organizer,
            boolean isActive) {
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
