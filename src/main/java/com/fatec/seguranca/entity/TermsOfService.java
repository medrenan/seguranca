package com.fatec.seguranca.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ter_terms_of_service")
public class TermsOfService {

    private Long version;
    private List<Alternative> alternatives;
    private List<User> users;
    private LogTermsOfService logTermsOfService;

    @Id
    @SequenceGenerator(name = "ter_terms_of_service_version_seq",
        sequenceName = "ter_terms_of_service_version_seq",
        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "ter_terms_of_service_version_seq")
    @Column(name = "ter_version", updatable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @OneToMany(mappedBy = "termsOfService")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ter_log_term_id")
    public LogTermsOfService getLogTermsOfService() {
        return logTermsOfService;
    }

    public void setLogTermsOfService(LogTermsOfService logTermsOfService) {
        this.logTermsOfService = logTermsOfService;
    }

    @ManyToMany
    @JoinTable(name = "ter_alt_term_alternatives",
            joinColumns = { @JoinColumn(name = "ter_version") },
            inverseJoinColumns = { @JoinColumn(name = "alt_id") }
    )
    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }
}
