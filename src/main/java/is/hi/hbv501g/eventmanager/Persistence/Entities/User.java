package is.hi.hbv501g.eventmanager.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Superclass for all account classes,
 * such as{@link Attendee} and {@link Organizer},
 * defining account parameters.
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class User {
    /** The database key.
     * Only generated in implementations of this class.
     * Therefore different implementations of this class will
     * have unlinked id generation, so they might have overlapping ids.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private @NonNull String username;
    private @NonNull String name;
    @JsonIgnore
    private @NonNull String email;
    @JsonIgnore
    private @Nullable String phoneNumber;
    @JsonIgnore
    private @NonNull String password;

    public User(@NonNull String username, @NonNull String name, @NonNull String email,
                @NonNull String phoneNumber, @NonNull String password){
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
