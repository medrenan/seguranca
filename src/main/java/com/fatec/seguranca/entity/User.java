package com.fatec.seguranca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr_user")
public class User {

    private Long id;
    private String name;
    private String password;
    private String email;
    private Set<Authorization> authorizations;
    private TermsOfService termsOfService;
    private List<Alternative> alternatives;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "usr_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "usr_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "uau_user_authorization",
            joinColumns = { @JoinColumn(name = "usr_id")},
            inverseJoinColumns = { @JoinColumn(name = "aut_id") }
    )
    public Set<Authorization> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Set<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usr_ter_version", nullable = true)
    public TermsOfService getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(TermsOfService termsOfService) {
        this.termsOfService = termsOfService;
    }

    @ManyToMany
    @JoinTable(name = "ual_user_alternatives",
            joinColumns = { @JoinColumn(name = "usr_id") },
            inverseJoinColumns = { @JoinColumn(name = "alt_id") }
    )
    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    @Column(name = "usr_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
