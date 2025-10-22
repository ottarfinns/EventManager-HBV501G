package is.hi.hbv501g.eventmanager.Requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateUserRequest {
    private Integer id; // only for Update, ignored for Create
    private String username;
    private String name;
    private String email ;
    private String phoneNumber; // optional
    private String password;
    private Boolean isOrganizer; // only for Create, ignored for Update

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
