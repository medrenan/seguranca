package com.fatec.seguranca.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Date;

public class JwtUtils {

    private static final String KEY = "spring.jwt.sec";

    public static String generateToken(Authentication user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Login userWithoutPassword = new Login();
        userWithoutPassword.setUsername(user.getName());
        if (!user.getAuthorities().isEmpty()) {
            userWithoutPassword.setAuthorization(user.getAuthorities().iterator().next().getAuthority());
        }
        String userJson = mapper.writeValueAsString(userWithoutPassword);
        Date now = new Date();
        Long hour = 1000L * 60L * 60L;
        return Jwts.builder().claim("userDetails", userJson).setIssuer("com.fatec").setSubject(user.getName())
                .setExpiration(new Date(now.getTime() + hour)).signWith(SignatureAlgorithm.HS512, KEY).compact();
    }

    public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String credentialsJson = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().get("userDetails", String.class);
        Login user = objectMapper.readValue(credentialsJson, Login.class);
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password("secret")
                .authorities(user.getAuthorization())
                .build();

        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), userDetails.getAuthorities());
    }
}
