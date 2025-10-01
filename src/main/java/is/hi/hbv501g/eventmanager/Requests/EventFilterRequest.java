package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event.eventType;
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
    private long venueId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private int maxOccupancy;
    private BigDecimal price;
    private long organizerId;
    private boolean isActive;
}
