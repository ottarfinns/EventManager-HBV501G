package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attendees")
@Getter @Setter
@NoArgsConstructor
public class Attendee extends User{
    //@OneToMany(mappedBy = "attendee")
    //private List<Tickets> tickets;

    public Attendee(String username, String name, String email, String phoneNumber, String password) {
        // super == User, calling User Constructor. This is why we can´t generate with lombok here
        super(username, name, email, phoneNumber, password);
    }

}
