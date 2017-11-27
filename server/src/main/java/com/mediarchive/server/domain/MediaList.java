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

    @Column(name = "LIST_INDEX")
    private int index;

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

    protected MediaList() {
    }

    public MediaList(User user, int index) {
        this.user = user;
        this.index = index;
        statistics = new Statistics(this, 1);
        System.out.println(statistics);
    }

    public Statistics stats() {
        return statistics;
    }
}
