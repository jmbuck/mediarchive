package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class MediaList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIST_ID")
    private Long list_id;

    @OneToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Movie> movies;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Series> series;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> books;


}
