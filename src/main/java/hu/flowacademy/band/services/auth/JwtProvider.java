package hu.flowacademy.band.services.auth;

import hu.flowacademy.band.configuration.PropertyConfig;
import hu.flowacademy.band.database.models.User;
import hu.flowacademy.band.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final Key key;

    JwtProvider(PropertyConfig propertyConfig) {
        key = Keys.hmacShaKeyFor(propertyConfig.getSecret().getBytes());
    }

    public Authentication resolver(String jwt) {
        try {
            Claims claims = (Claims) Jwts.parserBuilder().setSigningKey(key).build().parse(jwt).getBody();

            var roles = claims.get("roles", Roles[].class);

            return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Arrays.stream(roles)
                    .map(Enum::name)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return null;
        }
    }

    public String generate(User user) {
        var date = new Date();

        var dateExp = new Date(date.getTime() + 604_800_000); // 1 hét = 604'800'000 miliszekundum

        return Jwts.builder()
            .setIssuedAt(date)
            .setSubject(user.getEmail())
            .addClaims(Map.of("roles", user.getRoles()))
            .setExpiration(dateExp)
            .signWith(key)
            .compact();
    }
}
