package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Series implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEDIA_SID")
    private Long sid;

    @ManyToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MediaList mediaList;

    @Column(nullable = false, name = "LIST_INDEX")
    private int index;

    @Column(name = "API_ID")
    private String id;

    @Column(name = "DATE_STARTED")
    private long startEpoch;

    @Column(name = "DATE_FINISHED")
    private long finishedEpoch;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CURRENT_EPISODE")
    private int currentEpisode;

    protected Series() {
    }

    public Series(MediaList mediaList, int index, MediaDetails details) {
        this.mediaList = mediaList;
        this.index = index;
        this.id = details.getId();
        this.startEpoch = details.getStartEpoch();
        this.finishedEpoch = details.getFinishedEpoch();
        this.rating = details.getRating();
        this.title = details.getTitle();
        this.currentEpisode = details.getCurrent();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartEpoch() {
        return startEpoch;
    }

    public void setStartEpoch(long startEpoch) {
        this.startEpoch = startEpoch;
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

    public int getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(int currentEpisode) {
        this.currentEpisode = currentEpisode;
    }
}
