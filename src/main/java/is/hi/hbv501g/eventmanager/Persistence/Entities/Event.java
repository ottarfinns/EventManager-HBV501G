package is.hi.hbv501g.eventmanager.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import is.hi.hbv501g.eventmanager.Utilities.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

/**
 * Entity for Events.
 */
@Entity
@Table(name = "events")
@Getter @Setter
@NoArgsConstructor
public class Event {
    /**
     * Database key for an event.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private @NonNull String name;
    private EventType type;
    private String description;
    private LocalDateTime dateTime;
    private int maxPeople;
    private @NonNull Double price;
    @Embedded
    private @NonNull Venue venue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Organizer organizer;
    @JsonIgnore
    private boolean isActive;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Image image;




    public Event(
            String name,
            EventType type,
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
