package is.hi.hbv501g.eventmanager.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Account {
    private @Id @GeneratedValue long id;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean isOrganizer;

    // getters and setters
}
