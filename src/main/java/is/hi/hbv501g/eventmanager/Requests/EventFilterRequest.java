package is.hi.hbv501g.eventmanager.Requests;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event.eventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EventFilterRequest {
    private String name;
    private eventType type;
    private Long venueId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer maxOccupancy;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private Long organizerId;
}
