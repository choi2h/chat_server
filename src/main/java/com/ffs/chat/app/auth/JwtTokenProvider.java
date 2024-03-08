package com.ffs.chat.app.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String key;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey) {
        this.key = secretKey;
    }

    public String getPayload(final String token) {
        return tokenToJws(token).getBody().getSubject();
    }

    private Jws<Claims> tokenToJws(final String token) {
        System.out.println("token to jws=" + token);
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}
