package com.mediarchive.server.domain;

import java.io.Serializable;

public class MediaDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private int runtime;
    private int score;
    private String title;
    private String watched_date;
    private int episode_runtime;
    private int number_of_episodes;
    private int number_of_seasons;
    private String start_date;
    private String end_date;
    private int episodes_watched;
    private int seasons_watched;
    private int page_count;
    private String poster_path;

    public MediaDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWatched_date() {
        return watched_date;
    }

    public void setWatched_date(String watched_date) {
        this.watched_date = watched_date;
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

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public String toString() {
        return "" + id + "\n" +
                "" + runtime + "\n" +
                "" + score + "\n" +
                "" + title + "\n" +
                "" + watched_date + "\n" +
                "" + episode_runtime + "\n" +
                "" + number_of_episodes + "\n" +
                "" + number_of_seasons + "\n" +
                "" + start_date + "\n" +
                "" + end_date + "\n" +
                "" + episodes_watched + "\n" +
                "" + seasons_watched + "\n" +
                "" + page_count + "\n" +
                "" + poster_path + "\n";
    }
}
