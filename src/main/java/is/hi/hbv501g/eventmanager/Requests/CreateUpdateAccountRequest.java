package is.hi.hbv501g.eventmanager.Requests;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CreateUpdateAccountRequest {
    public final Integer id = null; // If create request, ignore this.
    public final String username = null;
    public final String name = null;
    public final String email = null;
    public final String phoneNumber = null;
    public final String password = null;
    public final @NonNull Boolean isOrganizer = false;
}
