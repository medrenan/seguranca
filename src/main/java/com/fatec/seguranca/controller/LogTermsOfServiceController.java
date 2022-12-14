package com.fatec.seguranca.controller;

import com.fatec.seguranca.entity.LogTermsOfService;
import com.fatec.seguranca.repository.LogTermsOfServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/log-terms-of-service")
@CrossOrigin
public class LogTermsOfServiceController {

    @Autowired
    LogTermsOfServiceRepository logTermsOfServiceRepository;

    @GetMapping
    List<LogTermsOfService> findAll() {
        return logTermsOfServiceRepository.findAll();
    }

    @GetMapping(value = "/findByEmailNotNull")
    List<LogTermsOfService> findAllEmailNotNull() {
        return logTermsOfServiceRepository.findByUserEmailIsNotNull();
    }

    @GetMapping(value = "/findByNameNotNull")
    List<LogTermsOfService> findByNameNotNull() {
        return logTermsOfServiceRepository.findByUserNameIsNotNull();
    }

}
