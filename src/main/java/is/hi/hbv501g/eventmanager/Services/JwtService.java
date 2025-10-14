package is.hi.hbv501g.eventmanager.Services;

public interface JwtService {
    public String generateToken(Integer userId, String role);

    public Integer getUserIdFromToken(String token);

    public boolean validateToken(String token);
}
