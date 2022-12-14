package com.fatec.seguranca.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "alt_alternatives")
public class Alternative {

    private Long id;
    private String description;
    private List<TermsOfService> termsOfService;
    private List<User> usersAccepted;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alt_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "alt_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(mappedBy = "alternatives")
    public List<TermsOfService> getTermsOfService() {
        return termsOfService;
    }

    public void setTermsOfService(List<TermsOfService> termsOfService) {
        this.termsOfService = termsOfService;
    }

    @ManyToMany(mappedBy = "alternatives")
    public List<User> getUsersAccepted() {
        return usersAccepted;
    }

    public void setUsersAccepted(List<User> usersAccepted) {
        this.usersAccepted = usersAccepted;
    }
}
