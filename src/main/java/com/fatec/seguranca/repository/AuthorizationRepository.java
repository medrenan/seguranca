package com.fatec.seguranca.repository;

import com.fatec.seguranca.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    public Authorization findByName(String name);
}
