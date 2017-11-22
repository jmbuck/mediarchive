package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MediaListService {
    Page<MediaList> getMediaList(User user, Pageable pageable);

    Page<Movie> getMovies(MediaList mediaList, Pageable pageable);

    Page<Series> getShows(MediaList mediaList, Pageable pageable);

    Page<Book> getBooks(MediaList mediaList, Pageable pageable);

    Movie getMovie(MediaList mediaList, int index);

    Movie addMovie(MediaList mediaList, MediaDetails details);

    Movie removeMovie(MediaList mediaList, int index);

    Series getShow(MediaList mediaList, int index);

    Series addShow(MediaList mediaList, MediaDetails details);

    Series removeShow(MediaList mediaList, int index);

    Book getBook(MediaList mediaList, int index);

    Book addBook(MediaList mediaList, MediaDetails details);

    Book removeBook(MediaList mediaList, int index);
}
