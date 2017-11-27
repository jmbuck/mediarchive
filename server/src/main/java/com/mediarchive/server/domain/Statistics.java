package com.mediarchive.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Statistics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "MEDIA_SID")
    private Long sid;

    @JsonIgnore
    @OneToOne
    private MediaList mediaList;

    @JsonIgnore
    @Column(name = "LIST_INDEX")
    private int index;

    @Column(name = "TOTAL_MOVIES")
    private int total_movies;

    @Column(name = "TOTAL_SHOWS")
    private int total_shows;

    @Column(name = "TOTAL_BOOKS")
    private int total_books;

    @Column(name = "TOTAL_MOVIE_SCORE")
    private int total_movie_score;

    @Column(name = "MEAN_MOVIE_SCORE")
    private double mean_movie_score;

    @Column(name = "TOTAL_BOOK_SCORE")
    private int total_book_score;

    @Column(name = "MEAN_BOOK_SCORE")
    private double mean_book_score;

    @Column(name = "TOTAL_SHOW_SCORE")
    private int total_show_score;

    @Column(name = "MEAN_SHOW_SCORE")
    private double mean_show_score;

    @Column(name = "TOTAL_MOVIE_RUNTIME")
    private int total_movie_runtime;

    @Column(name = "MEAN_MOVIE_RUNTIME")
    private int mean_movie_runtime;

    @Column(name = "TOTAL_PAGES")
    private int total_pages;

    @Column(name = "MEAN_PAGES")
    private int mean_pages;

    @Column(name = "TOTAL_EPISODES")
    private int total_episodes;

    @Column(name = "TOTAL_SEASONS")
    private int total_seasons;

    @Column(name = "TITLE")
    private int total_show_runtime;

    @Column(name = "MEAN_EPISODE_RUNTIME")
    private int mean_episode_runtime;

    protected Statistics() {
    }

    public Statistics(MediaList mediaList, int index) {
        this.mediaList = mediaList;
        this.index = index;
        this.total_episodes = 0;
        this.total_show_runtime = 0;
        this.mean_episode_runtime = 0;
        this.total_books = 0;
        this.total_pages = 0;
        this.mean_pages = 0;
        this.total_shows = 0;
        this.total_movies = 0;
        this.total_movie_runtime = 0;
        this.total_movie_score = 0;
        this.total_book_score = 0;
        this.total_show_score = 0;
        this.total_seasons = 0;
        this.mean_movie_runtime = 0;
        this.mean_movie_score = 0;
        this.mean_book_score = 0;
        this.mean_show_score = 0;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public MediaList getMediaList() {
        return mediaList;
    }

    public void setMediaList(MediaList mediaList) {
        this.mediaList = mediaList;
    }

    public int getTotal_movies() {
        return total_movies;
    }

    public void setTotal_movies(int total_movies) {
        this.total_movies = total_movies;
    }

    public int getTotal_shows() {
        return total_shows;
    }

    public void setTotal_shows(int total_shows) {
        this.total_shows = total_shows;
    }

    public int getTotal_books() {
        return total_books;
    }

    public void setTotal_books(int total_books) {
        this.total_books = total_books;
    }

    public int getTotal_movie_score() {
        return total_movie_score;
    }

    public void setTotal_movie_score(int total_movie_score) {
        this.total_movie_score = total_movie_score;
    }

    public double getMean_movie_score() {
        return mean_movie_score;
    }

    public void setMean_movie_score(double mean_movie_score) {
        this.mean_movie_score = mean_movie_score;
    }

    public int getTotal_book_score() {
        return total_book_score;
    }

    public void setTotal_book_score(int total_book_score) {
        this.total_book_score = total_book_score;
    }

    public double getMean_book_score() {
        return mean_book_score;
    }

    public void setMean_book_score(double mean_book_score) {
        this.mean_book_score = mean_book_score;
    }

    public int getTotal_show_score() {
        return total_show_score;
    }

    public void setTotal_show_score(int total_show_score) {
        this.total_show_score = total_show_score;
    }

    public double getMean_show_score() {
        return mean_show_score;
    }

    public void setMean_show_score(double mean_show_score) {
        this.mean_show_score = mean_show_score;
    }

    public int getTotal_movie_runtime() {
        return total_movie_runtime;
    }

    public void setTotal_movie_runtime(int total_movie_runtime) {
        this.total_movie_runtime = total_movie_runtime;
    }

    public int getMean_movie_runtime() {
        return mean_movie_runtime;
    }

    public void setMean_movie_runtime(int mean_movie_runtime) {
        this.mean_movie_runtime = mean_movie_runtime;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getMean_pages() {
        return mean_pages;
    }

    public void setMean_pages(int mean_pages) {
        this.mean_pages = mean_pages;
    }

    public int getTotal_episodes() {
        return total_episodes;
    }

    public void setTotal_episodes(int total_episodes) {
        this.total_episodes = total_episodes;
    }

    public int getTotal_seasons() {
        return total_seasons;
    }

    public void setTotal_seasons(int total_seasons) {
        this.total_seasons = total_seasons;
    }

    public int getTotal_show_runtime() {
        return total_show_runtime;
    }

    public void setTotal_show_runtime(int total_show_runtime) {
        this.total_show_runtime = total_show_runtime;
    }

    public int getMean_episode_runtime() {
        return mean_episode_runtime;
    }

    public void setMean_episode_runtime(int mean_episode_runtime) {
        this.mean_episode_runtime = mean_episode_runtime;
    }

    public void updateTotalMovies(int update) {
        this.total_movies += update;
    }

    public void updateTotalBooks(int update) {
        this.total_books += update;
    }

    public void updateTotalShows(int update) {
        this.total_shows += update;
    }

    public void updateTotalEpisodes(int update) {
        this.total_episodes += update;
    }

    public void updateTotalSeasons(int update) {
        this.total_seasons += update;
    }

    public void updateTotalPages(int update) {
        this.total_pages += update;
    }

    public void updateTotalMovieRuntime(int update) {
        this.total_movie_runtime += update;
    }

    public void updateTotalShowRuntime(int update) {
        this.total_show_runtime += update;
    }

    public void updateTotalMovieScore(int update) {
        this.total_movie_score += update;
    }

    public void updateTotalShowScore(int update) {
        this.total_show_score += update;
    }

    public void updateTotalBookScore(int update) {
        this.total_book_score += update;
    }
    
    public void updateMeans() {
        this.mean_movie_score = (int) ((double)this.total_movie_score / this.total_movies);
        this.mean_show_score = (int) ((double)this.total_show_score / this.total_shows);
        this.mean_book_score = (int) ((double)this.total_book_score / this.total_books);
        this.mean_movie_runtime = (int) ((double)this.total_movie_runtime / this.total_movies);
        this.mean_pages = (int) ((double)this.total_pages / this.total_books);
        this.mean_episode_runtime = (int) ((double)this.total_show_runtime / this.total_episodes);
    }
}
