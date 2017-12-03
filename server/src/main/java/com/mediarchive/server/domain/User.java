package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "USER_SID")
    private Long sid;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "COMPLETED_LIST")
    private MediaList mediaCompleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "UNDERWAY_LIST")
    private MediaList mediaUnderway;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "INTENT_LIST")
    private MediaList mediaIntent;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    protected User() {
        this.mediaCompleted = new MediaList(this);
        this.mediaUnderway = new MediaList(this);
        this.mediaIntent = new MediaList(this);
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.mediaCompleted = new MediaList(this);
        this.mediaUnderway = new MediaList(this);
        this.mediaIntent = new MediaList(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = encoder.encode(password);
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public MediaList getMediaCompleted() {
        return mediaCompleted;
    }

    public MediaList getMediaUnderway() {
        return mediaUnderway;
    }

    public MediaList getMediaIntent() {
        return mediaIntent;
    }

}
