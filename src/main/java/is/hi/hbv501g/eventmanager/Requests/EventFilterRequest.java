package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event.eventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EventFilterRequest {
    private String name;
    private eventType type;
    private Venue venue;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer maxOccupancy;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private Long organizerId;
}
