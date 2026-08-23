package myProject.jsonPlaceholder;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {



    private final String secret = "IVyhSXrQpk5tjdAt/Zy2ouq+OQYHZVxve+kW32r47Nk=";
    private SecretKey key;

    @PostConstruct
    public void init() {
        // Декодируем Base64-строку в секретный ключ
//        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
//        String secret = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        System.out.println("secret is:" + secret);
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(String username) {
        System.out.println("Generate key: " + key);
        return Jwts.builder()
                .subject(username) // Вместо setSubject()
                .issuedAt(new Date()) // Вместо setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // Вместо setExpiration()
                .signWith(key) // Использует SecretKey напрямую
                .compact();
    }

    public String extractUsername(String token) {
        System.out.println("Verify key: " + key);
        return Jwts.parser() // Вместо parserBuilder()
                .verifyWith(key) // Вместо setSigningKey()
                .build()
                .parseSignedClaims(token) // Вместо parseClaimsJws()
                .getPayload() // Вместо getBody()
                .getSubject();
    }
}
