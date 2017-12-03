package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "MEDIA_SID")
    private Long sid;

    @JsonIgnore
    @ManyToOne
    private MediaList mediaList;

    @Column(name = "API_ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "score")
    private int score;

    @Column(name = "WATCHED_DATE")
    private String watched_date;

    @Column(name = "RUNTIME")
    private int runtime;

    @Column(name = "POSTER_PATH")
    private String poster_path;

    protected Movie() {
    }

    public Movie(MediaList mediaList, MediaDetails details) {
        this.mediaList = mediaList;
        this.id = details.getId();
        this.title = details.getTitle();
        this.score = details.getScore();
        this.watched_date = details.getWatched_date();
        this.runtime = details.getRuntime();
        this.poster_path = details.getPoster_path();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getWatched_date() {
        return watched_date;
    }

    public void setWatched_date(String watched_date) {
        this.watched_date = watched_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Movie) {
            if (((Movie)object).getId().equals(this.id)) {
                return true;
            }
        }
        return false;
    }
}
