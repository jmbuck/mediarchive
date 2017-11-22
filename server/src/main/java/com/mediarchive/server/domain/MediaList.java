package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class MediaList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIST_ID")
    private Long list_id;

    @OneToOne(optional = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Movie> movies;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Show> shows;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Book> books;


}
