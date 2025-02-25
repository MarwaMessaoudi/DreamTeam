package team.project.redboost.authentif;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import team.project.redboost.entities.Role;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    // Add method to get the secret key
    public String getSecretKey() {
        return secretKey;
    }

    // Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(getSecretKey().getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Retrieve email from JWT token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a JWT token for email/password users (no provider or providerId).
     */
    public String generateToken(String email, Collection<? extends GrantedAuthority> authorities) {
        return generateToken(email, null, null, authorities); // Call the main method with null provider and providerId
    }

    /**
     * Generates a JWT token for OAuth2 users (with provider and providerId).
     */
    public String generateToken(String email, String provider, String providerId, Collection<? extends GrantedAuthority> authorities) {
        List<String> roleNames = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Key key = Keys.hmacShaKeyFor(getSecretKey().getBytes());

        JwtBuilder builder = Jwts.builder()
                .setSubject(email)
                .claim("roles", roleNames)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(key); // Use the Key object directly

        // Add provider and providerId only if they are not null
        if (provider != null) {
            builder.claim("provider", provider);
        }
        if (providerId != null) {
            builder.claim("providerId", providerId);
        }

        return builder.compact();
    }

    // Generate a refresh token for the user
    public String generateRefreshToken(String email, Collection<? extends GrantedAuthority> authorities) {
        List<String> roleNames = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Key key = Keys.hmacShaKeyFor(getSecretKey().getBytes());

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roleNames)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days validity
                .signWith(key) // Use the Key object directly
                .compact();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token
    public Boolean validateToken(String token, String email) {
        final String emailFromToken = extractEmail(token);
        return (emailFromToken.equals(email) && !isTokenExpired(token));
    }

    // Get roles from the token
    public List<Role> getRolesFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(getSecretKey().getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Use the Key object directly
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roleNames = claims.get("roles", List.class); // Extract roles as strings
        return roleNames.stream()
                .map(Role::valueOf)  // Convert strings back to Role enum
                .collect(Collectors.toList());
    }
}