package is.hi.hbv501g.eventmanager.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import is.hi.hbv501g.eventmanager.Utilities.EventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import lombok.*;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request object for filtering events.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventFilterRequest {
    private @Nullable String name;
    private @Nullable EventType type;
    private @Nullable Venue venue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private @Nullable LocalDateTime dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private @Nullable LocalDateTime dateTo;
    private @Nullable Integer maxOccupancy;
    private @Nullable BigDecimal maxPrice;
    private @Nullable BigDecimal minPrice;
    private @Nullable Long organizerId;
}
