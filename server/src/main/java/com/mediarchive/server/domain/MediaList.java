package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class MediaList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIST_ID")
    private Long list_id;

    @OneToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Movie> movies;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Series> series;

    @OneToMany//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;

    @OneToOne
    private Statistics statistics;
}
