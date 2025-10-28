package is.hi.hbv501g.eventmanager.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
