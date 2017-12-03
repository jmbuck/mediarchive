package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getUser(String name);
    User addUser(String body);

    Movie addMovie(String name, String details);
    Series addSeries(String name, String details);
    Book addBook(String name, String details);

    String getMovies(String name);
    String getSeries(String name);
    String getBooks(String name);
    String getStats(String name);

}
