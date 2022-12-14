package com.fatec.seguranca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_terms_of_service")
public class LogTermsOfService {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDateTime date;
    private Boolean accepted;
    private Long alternativeId;
    private TermsOfService termOfService;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_ter_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "log_ter_userid")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "log_ter_date")
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Column(name = "log_ter_accepted")
    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "log_ter_term_version")
    public TermsOfService getTermOfService() {
        return termOfService;
    }

    public void setTermOfService(TermsOfService termOfService) {
        this.termOfService = termOfService;
    }

    @Column(name = "log_ter_user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "log_ter_user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "log_ter_alt_id")
    public Long getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(Long alternativeId) {
        this.alternativeId = alternativeId;
    }
}
