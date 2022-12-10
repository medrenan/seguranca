package com.fatec.seguranca.repository;

import com.fatec.seguranca.entity.TermsOfService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TermsOfServiceRepository extends JpaRepository<TermsOfService, Long> {

    @Query("select ter from TermsOfService ter where ter.version = (select max(t.version) from TermsOfService t)")
    public TermsOfService findLatestTermsOfService();

    public TermsOfService findByVersion(Long version);
}
