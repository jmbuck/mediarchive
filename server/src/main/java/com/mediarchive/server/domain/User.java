package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "USER_SID")
    private Long sid;

    @JsonIgnore
    @OneToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPLETED_LIST")
    private MediaList mediaCompleted;

    @JsonIgnore
    @OneToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "UNDERWAY_LIST")
    private MediaList mediaUnderway;

    @JsonIgnore
    @OneToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INTENT_LIST")
    private MediaList mediaIntent;

    @JsonIgnore
    @Column(nullable = false, name = "LIST_INDEX")
    private int index;

    @Column(name = "USERNAME")
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    protected User() {
    }

    public User(String username, String password, int index) {
        this.username = username;
        this.password = password;
        this.index = index;
//        this.mediaCompleted = new MediaList();
//        this.mediaUnderway = new MediaList();
//        this.mediaIntent = new MediaList();
//        System.out.println("MEDIA COMPLETED " + mediaCompleted);
    }

    public void newLists() {
        this.mediaCompleted = new MediaList(this, 1);
        this.mediaUnderway = new MediaList(this, 1);
        this.mediaIntent = new MediaList(this, 1);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return  "\"username\": " + username + "";
    }
}
