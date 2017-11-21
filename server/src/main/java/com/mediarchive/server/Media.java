package com.mediarchive.server;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private long startEpoch;
    private long finishedEpoch;
    private int rating;

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
}
