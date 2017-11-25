package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MediaListService mediaListService;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String name) {
        return this.userRepository.findByName(name);
    }

    @Override
    public User addUser(String name, String password) {
        User user = new User(name, password, 1);
        return this.userRepository.save(user);
    }

    @Override
    public MediaList getMediaComplete(User user) {
        return user.getMediaCompleted();
    }

    @Override
    public MediaList getMediaUnderway(User user) {
        return user.getMediaCompleted();
    }

    @Override
    public MediaList getMediaIntent(User user) {
        return user.getMediaCompleted();
    }

    @Override
    public Movie addCompletedMovie(User user, MediaDetails mediaDetails) {
        return mediaListService.addMovie(user.getMediaCompleted(), mediaDetails);
    }

    @Override
    public List<Movie> getCompletedMovies(User user) {
        return mediaListService.getMovies(user.getMediaCompleted());
    }

    @Override
    public Series addCompletedSeries(User user, MediaDetails mediaDetails) {
        return mediaListService.addSeries(user.getMediaCompleted(), mediaDetails);
    }

    @Override
    public List<Series> getCompletedSeries(User user) {
        return mediaListService.getSeries(user.getMediaCompleted());
    }
    
    @Override
    public Book addCompletedBook(User user, MediaDetails mediaDetails) {
       return mediaListService.addBook(user.getMediaCompleted(), mediaDetails);
    }

    @Override
    public List<Book> getCompletedBooks(User user) {
        return mediaListService.getBooks(user.getMediaCompleted());
    }

    @Override
    public Movie addUnderwayMovie(User user, MediaDetails mediaDetails) {
        return mediaListService.addMovie(user.getMediaUnderway(), mediaDetails);
    }

    @Override
    public List<Movie> getUnderwayMovies(User user) {
        return mediaListService.getMovies(user.getMediaUnderway());
    }

    @Override
    public Series addUnderwaySeries(User user, MediaDetails mediaDetails) {
        return mediaListService.addSeries(user.getMediaUnderway(), mediaDetails);
    }

    @Override
    public List<Series> getUnderwaySeries(User user) {
        return mediaListService.getSeries(user.getMediaUnderway());
    }

    @Override
    public Book addUnderwayBook(User user, MediaDetails mediaDetails) {
        return mediaListService.addBook(user.getMediaUnderway(), mediaDetails);
    }

    @Override
    public List<Book> getUnderwayBooks(User user) {
        return mediaListService.getBooks(user.getMediaUnderway());
    }

    @Override
    public Movie addIntentMovie(User user, MediaDetails mediaDetails) {
        return mediaListService.addMovie(user.getMediaIntent(), mediaDetails);
    }

    @Override
    public List<Movie> getIntentMovies(User user) {
        return mediaListService.getMovies(user.getMediaIntent());
    }

    @Override
    public Series addIntentSeries(User user, MediaDetails mediaDetails) {
        return mediaListService.addSeries(user.getMediaIntent(), mediaDetails);
    }

    @Override
    public List<Series> getIntentSeries(User user) {
        return mediaListService.getSeries(user.getMediaIntent());
    }

    @Override
    public Book addIntentBook(User user, MediaDetails mediaDetails) {
        return mediaListService.addBook(user.getMediaIntent(), mediaDetails);
    }

    @Override
    public List<Book> getIntentBooks(User user) {
        return mediaListService.getBooks(user.getMediaIntent());
    }
}
