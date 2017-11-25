package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getUser(String name);

    User addUser(String name, String password);

//START Pageable pageable

    MediaList getMediaComplete(User user);

    MediaList getMediaUnderway(User user);

    MediaList getMediaIntent(User user);

    //----- COMPLETED -----
    Movie addCompletedMovie(User user, MediaDetails mediaDetails);
    Series addCompletedSeries(User user, MediaDetails mediaDetails);
    Book addCompletedBook(User user, MediaDetails mediaDetails);

    List<Movie> getCompletedMovies(User user);
    List<Series> getCompletedSeries(User user);
    List<Book> getCompletedBooks(User user);

    //----- UNDERWAY -----
    Movie addUnderwayMovie(User user, MediaDetails mediaDetails);
    Series addUnderwaySeries(User user, MediaDetails mediaDetails);
    Book addUnderwayBook(User user, MediaDetails mediaDetails);

    List<Movie> getUnderwayMovies(User user);
    List<Series> getUnderwaySeries(User user);
    List<Book> getUnderwayBooks(User user);
    
    //----- INTENT -----
    Movie addIntentMovie(User user, MediaDetails mediaDetails);
    Series addIntentSeries(User user, MediaDetails mediaDetails);
    Book addIntentBook(User user, MediaDetails mediaDetails);

    List<Movie> getIntentMovies(User user);
    List<Series> getIntentSeries(User user);
    List<Book> getIntentBooks(User user);

//END Pageable pageable

}
