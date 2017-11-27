package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "MEDIA_SID")
    private Long sid;

    @JsonIgnore
    @ManyToOne
    private MediaList mediaList;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @JsonIgnore
    @Column(name = "LIST_INDEX")
    private int index;

    @Column(name = "API_ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "score")
    private int score;

    @Column(name = "START_DATE")
    private String start_date;

    @Column(name = "END_DATE")
    private String end_date;

    @Column(name = "PAGE_COUNT")
    private int page_count;

    protected Book() {
    }

    public Book(MediaList mediaList, int index, MediaDetails details) {
        this.mediaList = mediaList;
        this.index = index;
        this.id = details.getId();
        this.title = details.getTitle();
        this.score = details.getScore();
        this.start_date = details.getStart_date();
        this.end_date = details.getEnd_date();
        this.page_count = details.getPage_count();
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Book) {
            if (((Book)object).getId().equals(this.id)) {
                return true;
            }
        }
        return false;
    }
}
