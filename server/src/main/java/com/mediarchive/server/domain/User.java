package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_SID")
    private Long sid;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MediaList mediaList;

    @Column(nullable = false, name = "LIST_INDEX")
    private int index;

    @Column(nullable = false, name = "NAME")
    private String name;

    @JsonIgnore
    private String password;

    protected User() {
    }

    public User(String name, String password, int index) {
        this.name = name;
        this.password = password;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
