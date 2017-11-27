package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;

import java.util.List;

public interface MediaListService {

    void saveLists(User user);

    void saveStats(Statistics s);

    List<Movie> getMovies(MediaList mediaList);

    List<Series> getSeries(MediaList mediaList);

    List<Book> getBooks(MediaList mediaList);

    Statistics getStatistics(MediaList mediaList);

    Movie getMovie(MediaList mediaList, String id);

    Movie getMovie(MediaList mediaList, int index);

    Movie addMovie(MediaList mediaList, MediaDetails details);

    Movie removeMovie(MediaList mediaList, String id);

    Series getSeries(MediaList mediaList, String id);

    Series getSeries(MediaList mediaList, int index);

    Series addSeries(MediaList mediaList, MediaDetails details);

    Series removeSeries(MediaList mediaList, String id);

    Book getBook(MediaList mediaList, String id);

    Book getBook(MediaList mediaList, int index);

    Book addBook(MediaList mediaList, MediaDetails details);

    Book removeBook(MediaList mediaList, String id);

}
