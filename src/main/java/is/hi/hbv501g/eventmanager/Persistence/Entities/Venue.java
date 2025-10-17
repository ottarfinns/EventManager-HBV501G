package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Venue {
    private String address;
    private String postCode;
    private String venueName;
}
