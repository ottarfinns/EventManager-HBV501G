package is.hi.hbv501g.eventmanager.Persistence.Entities;

public abstract class User {
    private String username;
    private String name;
    private String email;
    private String phoneNumber;

    public User(String username, String name, String email, String phoneNumber) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
