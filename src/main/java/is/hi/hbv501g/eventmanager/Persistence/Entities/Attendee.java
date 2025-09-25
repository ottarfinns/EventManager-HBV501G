package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "attendees")
public class Attendee extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Attendee(String username, String name, String email, String phoneNumber) {
        super(username, name, email, phoneNumber);
    }

    // getters and setters

    @Override
    public Integer getId() {
        return id;
    }
}
