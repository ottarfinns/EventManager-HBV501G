package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizer")
@Getter @Setter
@NoArgsConstructor
public class Organizer extends User{
    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();

    public Organizer(String username, String name, String email, String phoneNumber, String password) {
        super(username, name, email, phoneNumber, password); // super == User, calling User Constructor
    }
}
