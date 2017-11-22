package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEDIA_SID")
    private Long sid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MediaList mediaList;

    @Column(nullable = false, name = "LIST_INDEX")
    private int index;

    @Column(name = "API_ID")
    private String id;

    @Column(name = "DATE_FINISHED")
    private long finishedEpoch;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "TITLE")
    private String title;

    protected Movie() {
    }

    public Movie(MediaList mediaList, int index, MediaDetails details) {
        this.mediaList = mediaList;
        this.index = index;
        this.id = details.getId();
        this.finishedEpoch = details.getFinishedEpoch();
        this.rating = details.getRating();
        this.title = details.getTitle();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFinishedEpoch() {
        return finishedEpoch;
    }

    public void setFinishedEpoch(long finishedEpoch) {
        this.finishedEpoch = finishedEpoch;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
