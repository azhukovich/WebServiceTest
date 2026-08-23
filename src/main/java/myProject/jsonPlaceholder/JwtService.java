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

    private final String secret = "uA1r9f2q4s7v9xC3F6J9L2Q5N8R1T4W7Z0B3E6H9K2M5P8S1U4X7A0D3G6J9L2Q5";
    private SecretKey key;

    @PostConstruct
    public void init() {
        // Декодируем Base64-строку в секретный ключ
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Вместо setSubject()
                .issuedAt(new Date()) // Вместо setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // Вместо setExpiration()
                .signWith(key) // Использует SecretKey напрямую
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser() // Вместо parserBuilder()
                .verifyWith(key) // Вместо setSigningKey()
                .build()
                .parseSignedClaims(token) // Вместо parseClaimsJws()
                .getPayload() // Вместо getBody()
                .getSubject();
    }
}
