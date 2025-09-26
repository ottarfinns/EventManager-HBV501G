package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter @Setter
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Integer id;
    private @NonNull String name;
    private String type;
    private String description;
    private LocalDateTime dateTime;
    private int maxPeople;
    private @NonNull Double price;
    @ManyToOne(fetch = FetchType.LAZY)
    private @NonNull Venue venue;
    @ManyToOne(fetch = FetchType.LAZY)
    private final Organizer organizer;
}
