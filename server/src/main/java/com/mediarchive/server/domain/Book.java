package com.mediarchive.server.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEDIA_SID")
    private Long sid;

    @ManyToOne(optional = false)
    private MediaList mediaList;

    @Column(name = "ID")
    private String id;

    @Column(name = "DATE_STARTED")
    private long startEpoch;

    @Column(name = "DATE_FINISHED")
    private long finishedEpoch;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CURRENT_PAGE")
    private int currentPage;

    public Book(String id) {

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
