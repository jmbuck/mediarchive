package com.mediarchive.server;

import javax.persistence.Entity;

@Entity
public class Movie extends Media {

    public Movie(String id) {
        setId(id);
    }

    public void setFinishedEpoch(int epoch) {
        super.setFinishedEpoch(epoch);
        setStartEpoch(epoch);
    }
}
