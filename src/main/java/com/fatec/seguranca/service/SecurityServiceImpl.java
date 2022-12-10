package com.fatec.seguranca.service;

import com.fatec.seguranca.entity.Authorization;
import com.fatec.seguranca.entity.User;
import com.fatec.seguranca.repository.AuthorizationRepository;
import com.fatec.seguranca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Override
    public User newUser(User user) {
        if (user.getAuthorizations() == null) {
            HashSet<Authorization> authorizations = new HashSet<>();
            Authorization auth = authorizationRepository.findByName("ROLE_USER");
            authorizations.add(auth);
            user.setAuthorizations(authorizations);
        }

        return userRepository.save(user);
    }

    @Override
    public User newUser(String name, String password) {
        if(name == null || password == null) {
            return null;
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        return newUser(user);
    }

    @Override
    @Transactional
    public User newUser(String name, String password, String authorization) {
        if(name == null || password == null || authorization == null) {
            return null;
        }
        Authorization auth = authorizationRepository.findByName(authorization);
        if(auth == null) {
            auth = new Authorization();
            auth.setName(authorization);
            authorizationRepository.save(auth);
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAuthorizations(new HashSet<Authorization>());
        user.getAuthorizations().add(auth);

        return newUser(user);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public List<User> findByName(String name) {
        if(name == null || name.isBlank()) {
            return allUsers();
        }
        return userRepository.findByNameContains(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        return org.springframework.security.core.userdetails.User.builder().username(username).password(user.getPassword())
                .authorities(user.getAuthorizations().stream()
                        .map(Authorization::getName).collect(Collectors.toList())
                        .toArray(new String[user.getAuthorizations().size()]))
                .build();
    }

}
