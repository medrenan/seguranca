package com.fatec.seguranca.repository;

import com.fatec.seguranca.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByName(String name);

    public List<User> findByNameContains(String name);

    public List<User> findByAuthorizationsName(String authorization);

    public List<User> findByAuthorizations(String authorization);
}
