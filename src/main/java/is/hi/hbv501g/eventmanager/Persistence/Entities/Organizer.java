package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizer")
@Getter @Setter
public class Organizer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();

    public Organizer(String username, String name, String email, String phoneNumber, String password) {
        super(username, name, email, phoneNumber, password); // super == User, calling User Constructor
    }

    @Override
    public Integer getId() {
        return id;
    }
}
