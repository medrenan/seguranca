package com.fatec.seguranca.service;

import com.fatec.seguranca.entity.TermsOfService;

import java.util.List;

public interface TermsOfServiceService {

    public TermsOfService newTermsOfService(TermsOfService termsOfService);

    public List<TermsOfService> allTermsOfService();

    public TermsOfService findByVersion(Long version);

    public TermsOfService findLatestTerm();

    public Boolean accept(String username);

    Boolean userTermAccepted(String name);
}
