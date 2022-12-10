package com.fatec.seguranca.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ter_terms_of_service")
public class TermsOfService {

    private Long version;
    private String description;
    private List<User> users;

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

    @Column(name = "ter_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "termsOfService")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
