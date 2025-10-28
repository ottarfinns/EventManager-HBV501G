package is.hi.hbv501g.eventmanager.Requests;

import lombok.*;

/**
 * Request object for creating and updating User accounts.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateUserRequest {
    /**
     * The database id. Only used for updating accounts,
     * if this field is nonnull for createAccount it is ignored.
     */
    private Integer id;
    private String username;
    private String name;
    private String email ;
    /**
     * Phone number of the user, optional.
     */
    private String phoneNumber; // optional
    private String password;
    /**
     * If true, an organizer account will be created.
     * If false, an attendee account will be created.
     * Used in conjunction with id to identify account in case of updating.
     */
    private Boolean isOrganizer;

    /**
     * Constructor for the case of updating User accounts.
     * @param id The database id of the User to be updated. Does not change.
     */
    public CreateUpdateUserRequest(Integer id, String username, String name, String email, String phoneNumber, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    /**
     * Constructor for the case of creating User accounts.
     */
    public CreateUpdateUserRequest(String username, String name, String email, String phoneNumber, String password, Boolean isOrganizer) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isOrganizer = isOrganizer;
    }
}
