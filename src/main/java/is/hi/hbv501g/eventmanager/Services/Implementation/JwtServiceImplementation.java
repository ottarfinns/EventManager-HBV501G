package is.hi.hbv501g.eventmanager.Services.Implementation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import is.hi.hbv501g.eventmanager.Services.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtServiceImplementation implements JwtService {
    private static final String SECRET = System.getenv("JWT_SECRET");
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    public String generateToken(Integer userId, String userType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("userType", userType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public Integer getUserIdFromToken(String token) {
        return 0;
    }

    public boolean validateToken(String token) {
        return false;
    }
}
