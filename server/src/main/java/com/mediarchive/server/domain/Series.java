package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Series implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "MEDIA_SID")
    private Long sid;

    @JsonIgnore
    @ManyToOne
    private MediaList mediaList;

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

    @Column(name = "EPISODES_WATCHED")
    private int episodes_watched;

    @Column(name = "SEASONS_WATCHED")
    private int seasons_watched;

    @Column(name = "EPISODE_RUNTIME")
    private int episode_runtime;

    @Column(name = "NUMBER_OF_EPISODES")
    private int number_of_episodes;

    @Column(name = "NUMBER_OF_SEASONS")
    private int number_of_seasons;

    protected Series() {
    }

    public Series(MediaList mediaList, int index, MediaDetails details) {
        this.mediaList = mediaList;
        this.index = index;
        this.id = details.getId();
        this.title = details.getTitle();
        this.score = details.getScore();
        this.start_date = details.getStart_date();
        this.end_date = details.getEnd_date();
        this.episodes_watched = details.getEpisodes_watched();
        this.seasons_watched = details.getSeasons_watched();
        this.episode_runtime = details.getEpisode_runtime();
        this.number_of_episodes = details.getNumber_of_episodes();
        this.number_of_seasons = details.getNumber_of_seasons();
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

    public int getEpisodes_watched() {
        return episodes_watched;
    }

    public void setEpisodes_watched(int episodes_watched) {
        this.episodes_watched = episodes_watched;
    }

    public int getSeasons_watched() {
        return seasons_watched;
    }

    public void setSeasons_watched(int seasons_watched) {
        this.seasons_watched = seasons_watched;
    }

    public int getEpisode_runtime() {
        return episode_runtime;
    }

    public void setEpisode_runtime(int episode_runtime) {
        this.episode_runtime = episode_runtime;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Series) {
            if (((Series)object).getId().equals(this.id)) {
                return true;
            }
        }
        return false;
    }
}
