package com.mediarchive.server;

public class Movie extends Media {
    private long epoch;

    public void setDate(long epoch) {
            this.epoch = epoch;
    }

    public long getDate() {
            return epoch;
    }
}
