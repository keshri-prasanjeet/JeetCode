package org.jeet.JeetCode.Utility;
import java.util.Date;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    public static final String SECRET = "MySecretKey";
    private static final long EXPIRATION_TIME =  864000000;//10 days
    public static String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public static String extractUserName(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
