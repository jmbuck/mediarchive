package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getUser(String name);

    User addUser(String body);

    Statistics getCompletedStatistics(String name);
    Statistics getUnderwayStatistics(String name);
    Statistics getIntentStatistics(String name);

    //----- COMPLETED -----
    Movie addCompletedMovie(String name, String details);
    Series addCompletedSeries(String name, String details);
    Book addCompletedBook(String name, String details);

    List<Movie> getCompletedMovies(String name);
    List<Series> getCompletedSeries(String name);
    List<Book> getCompletedBooks(String name);

    Movie removeCompletedMovie(String name, String id);
    Series removeCompletedSeries(String name, String id);
    Book removeCompletedBook(String name, String id);

    //----- UNDERWAY -----
    Movie addUnderwayMovie(String name, String details);
    Series addUnderwaySeries(String name, String details);
    Book addUnderwayBook(String name, String details);

    List<Movie> getUnderwayMovies(String name);
    List<Series> getUnderwaySeries(String name);
    List<Book> getUnderwayBooks(String name);

    Movie removeUnderwayMovie(String name, String id);
    Series removeUnderwaySeries(String name, String id);
    Book removeUnderwayBook(String name, String id);
    
    //----- INTENT -----
    Movie addIntentMovie(String name, String details);
    Series addIntentSeries(String name, String details);
    Book addIntentBook(String name, String details);

    List<Movie> getIntentMovies(String name);
    List<Series> getIntentSeries(String name);
    List<Book> getIntentBooks(String name);

    Movie removeIntentMovie(String name, String id);
    Series removeIntentSeries(String name, String id);
    Book removeIntentBook(String name, String id);

}
