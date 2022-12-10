package com.fatec.seguranca.controller;

import com.fatec.seguranca.entity.User;
import com.fatec.seguranca.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    SecurityService securityService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> findAll() {
        return securityService.allUsers();
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id") Long id) {
        return securityService.findById(id);
    }

    @GetMapping(value = "/name/{name}")
    public List<User> findByName(@PathVariable("name") String name) {
        return securityService.findByName(name);
    }

    @PostMapping
    public User newUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return securityService.newUser(user);
    }
}
