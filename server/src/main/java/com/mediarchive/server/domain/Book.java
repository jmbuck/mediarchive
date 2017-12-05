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

    @Column(name = "API_ID", nullable = false)
    private String id;

    @Lob
    @Column(name = "TITLE", length = 65536)
    private String title;

    @Column(name = "score")
    private int score;

    @Column(name = "START_DATE")
    private String start_date;

    @Column(name = "END_DATE")
    private String end_date;

    @Column(name = "PAGE_COUNT")
    private int page_count;

    @Column(name = "CATEGORY")
    private String category;

    @Lob
    @Column(name = "PATH", length = 65536)
    private String path;

    protected Book() {
    }

    public Book(MediaList mediaList, MediaDetails details) {
        this.mediaList = mediaList;

        this.id = details.getId();
        this.title = details.getTitle();
        this.score = details.getScore();
        this.start_date = details.getStart_date();
        this.end_date = details.getEnd_date();
        this.page_count = details.getPage_count();
        this.category = details.getCategory();
        this.path = details.getPath();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
