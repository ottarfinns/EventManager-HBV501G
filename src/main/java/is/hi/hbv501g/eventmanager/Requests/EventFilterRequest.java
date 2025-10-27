package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.EventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventFilterRequest {
    private String name;
    private EventType type;
    private Venue venue;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer maxOccupancy;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private Long organizerId;
}
