package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_SID")
    private Long sid;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPLETED_LIST")
    private MediaList mediaCompleted;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "UNDERWAY_LIST")
    private MediaList mediaUnderway;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "INTENT_LIST")
    private MediaList mediaIntent;

    @Column(nullable = false, name = "LIST_INDEX")
    private int index;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    protected User() {
    }

    public User(String name, String password, int index) {
        this.name = name;
        this.password = password;
        this.index = index;
        this.mediaCompleted = new MediaList();
        this.mediaUnderway = new MediaList();
        this.mediaIntent = new MediaList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return  "\"name\": " + name + "";
    }
}
