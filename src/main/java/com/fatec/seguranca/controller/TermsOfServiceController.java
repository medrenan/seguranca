package com.fatec.seguranca.controller;

import com.fatec.seguranca.entity.TermsOfService;
import com.fatec.seguranca.service.TermsOfServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/terms-of-service")
@CrossOrigin
public class TermsOfServiceController {

    @Autowired
    TermsOfServiceService termsService;

    @GetMapping
    public List<TermsOfService> findAll() {
        return termsService.allTermsOfService();
    }

    @GetMapping(value = "/latest")
    public TermsOfService latestTerm() {
        return termsService.findLatestTerm();
    }

    @GetMapping(value = "/version/{version}")
    public TermsOfService findByVersion(@PathVariable Long version) {
        return termsService.findByVersion(version);
    }

    @PostMapping
    public TermsOfService newTermOfService(@RequestBody TermsOfService terms) {
        return termsService.newTermsOfService(terms);
    }

    // User accepts latest version of the Terms of Service
    @GetMapping(value = "/accept")
    public Boolean accept(Principal principal) {
        return termsService.accept(principal.getName());
    }

    @GetMapping(value = "/verify")
    public Boolean isLastTermAccepted(Principal principal) {
        return termsService.userTermAccepted(principal.getName());
    }

}
