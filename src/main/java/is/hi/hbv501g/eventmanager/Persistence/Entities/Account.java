package is.hi.hbv501g.eventmanager.Persistence.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean isOrganizer;

    // getters and setters
}
