package is.hi.hbv501g.eventmanager.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateUserRequest {
    public Integer id; // only for Update, ignored for Create
    public String username;
    public String name;
    public String email ;
    public String phoneNumber; // optional
    public String password;
    public Boolean isOrganizer; // only for Create, ignored for Update

    // Constructor for Update
    public CreateUpdateUserRequest(Integer id, String username, String name, String email, String phoneNumber, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    // Constructor for Create
    public CreateUpdateUserRequest(String username, String name, String email, String phoneNumber, String password, Boolean isOrganizer) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isOrganizer = isOrganizer;
    }
}
