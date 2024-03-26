package com.ffs.chat.app.auth;

import com.ffs.chat.app.auth.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String key;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey) {
        this.key = secretKey;
    }

    public String getPayload(String token) {
        token = token.replace("Bearer ", "");
        return tokenToJws(token).getBody().getSubject();
    }

    public boolean validateAbleToken(final String token) {

        final Jws<Claims> claims;
        try {
            claims = tokenToJws(token);
        }catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }

        System.out.println("claims = " + claims);
        return validateExpiredToken(claims);
    }

    public String resolveToken(String token) {
        if(token != null && token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }

        return null;
    }

    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    private Jws<Claims> tokenToJws(final String token) {
        System.out.println("token to jws=" + token);
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }

    private boolean validateExpiredToken(final Jws<Claims> claims) {
        Date expiration = claims.getBody().getExpiration();
        Date now = new Date();
        System.out.println("expiration=" + expiration + "now=" + now);

        if(expiration.before(now)) {
            throw new InvalidTokenException("만료된 토큰입니다.");
        }

        return true;
    }
}
