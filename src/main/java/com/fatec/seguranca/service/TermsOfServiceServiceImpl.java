package com.fatec.seguranca.service;

import com.fatec.seguranca.entity.LogTermsOfService;
import com.fatec.seguranca.entity.TermsOfService;
import com.fatec.seguranca.entity.User;
import com.fatec.seguranca.repository.LogTermsOfServiceRepository;
import com.fatec.seguranca.repository.TermsOfServiceRepository;
import com.fatec.seguranca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TermsOfServiceServiceImpl implements TermsOfServiceService{

    @Autowired
    TermsOfServiceRepository termsOfServiceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LogTermsOfServiceRepository logTermsOfServiceRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TermsOfService newTermsOfService(TermsOfService termsOfService) {
        return termsOfServiceRepository.save(termsOfService);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<TermsOfService> allTermsOfService() {
        return termsOfServiceRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TermsOfService findByVersion(Long version) {
        Optional<TermsOfService> termsOfServiceOptional = termsOfServiceRepository.findById(version);
        if(termsOfServiceOptional.isPresent()) {
            return termsOfServiceOptional.get();
        }
        throw new RuntimeException("Term of Service not found");
    }

    @Override
    public TermsOfService findLatestTerm() {
        return termsOfServiceRepository.findLatestTermsOfService();
    }

    @Override
    public Boolean accept(String username) {
        if (username == null || username.isBlank()) {
            throw new RuntimeException("You need to inform the user who is accepting the Terms of Service");
        }
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setTermsOfService(this.findLatestTerm());
        userRepository.save(user);
        logTermsOfServiceByUserAction(user);

        return true;
    }

    // Verifica que o usu??rio aceitou o ??ltima vers??o dos Termos de Servi??o
    @Override
    public Boolean userTermAccepted(String name) {
        User user = userRepository.findByName(name);
        TermsOfService userTerms = user.getTermsOfService();

        if (userTerms == null) {
            return false;
        }
        Long userAcceptedVersion = user.getTermsOfService().getVersion();
        int versionCompare = userAcceptedVersion.compareTo(termsOfServiceRepository.findLatestTermsOfService().getVersion());

        return versionCompare >= 0;
    }

    private void logTermsOfServiceByUserAction(User user) {
        LogTermsOfService log = new LogTermsOfService();

        log.setUserId(user.getId());
        log.setTermOfService(user.getTermsOfService());
        log.setAccepted(user.getTermsOfService() != null);
        log.setDate(LocalDateTime.now());

        logTermsOfServiceRepository.save(log);
    }
}
