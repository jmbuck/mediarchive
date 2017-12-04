package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getUser(String name);
    User addUser(String body);
    User addUser(String username, String password);
    User getUser(String username, String password);

    String getMovies(String name);
    String getSeries(String name);
    String getBooks(String name);
    String getStats(String name);

    //----- COMPLETED -----
    Movie addCompletedMovie(String name, String details);
    Series addCompletedSeries(String name, String details);
    Book addCompletedBook(String name, String details);

    Movie removeCompletedMovie(String name, String id);
    Series removeCompletedSeries(String name, String id);
    Book removeCompletedBook(String name, String id);

    //----- UNDERWAY -----
    Series addUnderwaySeries(String name, String details);
    Book addUnderwayBook(String name, String details);

    Series removeUnderwaySeries(String name, String id);
    Book removeUnderwayBook(String name, String id);

    //----- INTENT -----
    Movie addIntentMovie(String name, String details);
    Series addIntentSeries(String name, String details);
    Book addIntentBook(String name, String details);

    Movie removeIntentMovie(String name, String id);
    Series removeIntentSeries(String name, String id);
    Book removeIntentBook(String name, String id);

}
