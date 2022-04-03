package ar.com.minigt.zerowork.todoapi.security;

import static io.jsonwebtoken.SignatureAlgorithm.*;

import ar.com.minigt.zerowork.todoapi.models.User;
import ar.com.minigt.zerowork.todoapi.models.UserDetails;
import ar.com.minigt.zerowork.todoapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;


    public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
    public static final Long EXPIRATION_TIME = 10000000L;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User applicationUser = new ObjectMapper().readValue(req.getInputStream(), User.class);
            applicationUser.setPasswordHash(UserService.encrypt(applicationUser.getPassword()));
            UserDetails user = new UserDetails(applicationUser);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user,
                            applicationUser.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        Claims claims = Jwts.claims().setSubject(((UserDetails) auth.getPrincipal()).getUsername());
        claims.put("user", new ObjectMapper().writeValueAsString(((UserDetails) auth.getPrincipal()).getUser()));
        String token = Jwts.builder().setClaims(claims).signWith(key, HS512).setExpiration(exp).compact();
        res.addHeader("token", token);
    }
}