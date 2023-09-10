package borelgabriel.com.br.springwebstore.security;

import borelgabriel.com.br.springwebstore.ApplicationContextLoad;
import borelgabriel.com.br.springwebstore.model.User;
import borelgabriel.com.br.springwebstore.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class JWTTokenAuthenticationService {
    private static final long TOKEN_EXPIRATION_TIME = 172800000; // 2 days in milliseconds
    private static final String SECRET = "secret_key";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws IOException {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(HEADER_STRING, token);
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        this.liberateCors(response);
        String tokenHeader = request.getHeader(HEADER_STRING);
        if (tokenHeader == null) return null;

        String token = tokenHeader.replace(TOKEN_PREFIX, "").trim();
        String username = Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if (username == null) return null;

        User user = ApplicationContextLoad
                .getApplicationContext()
                .getBean(UserRepository.class)
                .findUserByLogin(username);
        if (user == null) return null;

        return new UsernamePasswordAuthenticationToken(
                user.getLogin(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    private void liberateCors(HttpServletResponse response) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}
