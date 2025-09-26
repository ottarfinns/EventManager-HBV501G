package is.hi.hbv501g.eventmanager.Persistence.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class User {
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    public abstract Integer getId();
}
