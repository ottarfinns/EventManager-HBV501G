package is.hi.hbv501g.eventmanager.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Account class for event organizers,
 * who are capable of managing events
 * but will not book tickets to any events.
 */
@Entity
@Table(name = "organizer")
@Getter @Setter
@NoArgsConstructor
public class Organizer extends User{
    @OneToMany(mappedBy = "organizer")
    @JsonIgnore
    private List<Event> events = new ArrayList<>();

    public Organizer(String username, String name, String email, String phoneNumber, String password) {
        super(username, name, email, phoneNumber, password); // super == User, calling User Constructor
    }
}
