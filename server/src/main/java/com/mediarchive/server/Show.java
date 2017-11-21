package com.mediarchive.server;

import javax.persistence.Entity;

@Entity
public class Show extends Media {

    private int currentEpisode;

    public Show(String id) {
        setId(id);
    }

    public int getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(int currentEpisode) {
        this.currentEpisode = currentEpisode;
    }
}
