package com.mediarchive.server.domain;

import java.io.Serializable;

public class MediaDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private long startEpoch;
    private long finishedEpoch;
    private int rating;
    private String title;
    private int current;

    public MediaDetails() {
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

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
