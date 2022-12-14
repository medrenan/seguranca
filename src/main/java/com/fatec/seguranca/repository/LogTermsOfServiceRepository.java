package com.fatec.seguranca.repository;

import com.fatec.seguranca.entity.LogTermsOfService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogTermsOfServiceRepository extends JpaRepository<LogTermsOfService, Long> {

    public List<LogTermsOfService> findByUserEmailIsNotNull();

    public List<LogTermsOfService> findByUserNameIsNotNull();

}
