package is.hi.hbv501g.eventmanager.Services.Implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import is.hi.hbv501g.eventmanager.Services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImplementation implements JwtService {

    private static final long EXPIRATION_TIME = 24L * 60 * 60 * 1000;

    private final SecretKey key;

    public JwtServiceImplementation(
            @Value("${app.security.jwt.secret:}") String secret,
            @Value("${app.security.jwt.base64:false}") boolean secretIsBase64
    ) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret is missing. Set app.security.jwt.secret or JWT_SECRET env var.");
        }

        byte[] keyBytes = secretIsBase64
                ? Decoders.BASE64.decode(secret)
                : secret.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 64) {
            throw new IllegalStateException("JWT secret too short for HS512 (needs at least 64 bytes).");
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(Integer userId, String userType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("userType", userType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public String getUserTypeFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userType").toString();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
