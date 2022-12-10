package com.fatec.seguranca.service;

import com.fatec.seguranca.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface SecurityService extends UserDetailsService {

    public User newUser(User user);

    public User newUser(String name, String password);

    public User newUser(String name, String password, String authorization);

    public List<User> allUsers();

    public User findById(Long id);

    public List<User> findByName(String name);
}
