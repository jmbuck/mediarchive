package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;

import java.util.List;

public interface MediaListService {

    //START used to have Pageable pageable and Page<>
    List<Movie> getMovies(MediaList mediaList);

    List<Series> getSeries(MediaList mediaList);

    List<Book> getBooks(MediaList mediaList);
    //END used to have Pageable pageable and Page<>

    Movie getMovie(MediaList mediaList, int index);

    Movie addMovie(MediaList mediaList, MediaDetails details);

    Movie removeMovie(MediaList mediaList, int index);

    Series getSeries(MediaList mediaList, int index);

    Series addSeries(MediaList mediaList, MediaDetails details);

    Series removeSeries(MediaList mediaList, int index);

    Book getBook(MediaList mediaList, int index);

    Book addBook(MediaList mediaList, MediaDetails details);

    Book removeBook(MediaList mediaList, int index);
}
