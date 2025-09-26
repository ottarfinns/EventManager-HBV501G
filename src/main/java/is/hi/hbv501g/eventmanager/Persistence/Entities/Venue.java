package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "venues")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;
    private String postCode;
    private String undefined; // for just writing text
}
