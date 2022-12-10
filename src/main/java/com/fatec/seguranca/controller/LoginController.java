package com.fatec.seguranca.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fatec.seguranca.security.JwtUtils;
import com.fatec.seguranca.security.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping
    public Login authenticate(@RequestBody Login login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        auth = authManager.authenticate(auth);
        login.setPassword(null);
        login.setToken(JwtUtils.generateToken(auth));
        if(!auth.getAuthorities().isEmpty()) {
            login.setAuthorization(auth.getAuthorities().iterator().next().getAuthority());
        }
        return login;
    }
}
