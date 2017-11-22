package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component("mediaListService")
@Transactional
public class MediaListServiceImpl implements MediaListService {

    private final MediaListRepository mediaListRepository;
    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;
    private final BookRepository bookRepository;

    public MediaListServiceImpl(MediaListRepository mediaListRepository,
                                MovieRepository movieRepository,
                                SeriesRepository seriesRepository,
                                BookRepository bookRepository) {
        this.mediaListRepository = mediaListRepository;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<MediaList> getMediaList(User user, Pageable pageable) {
        return this.mediaListRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Movie> getMovies(MediaList mediaList, Pageable pageable) {
        return this.movieRepository.findByMediaList(mediaList, pageable);
    }

    @Override
    public Page<Series> getShows(MediaList mediaList, Pageable pageable) {
        return this.seriesRepository.findByMediaList(mediaList, pageable);
    }

    @Override
    public Page<Book> getBooks(MediaList mediaList, Pageable pageable) {
        return this.bookRepository.findByMediaList(mediaList, pageable);
    }

    @Override
    public Movie getMovie(MediaList mediaList, int index) {
        return this.movieRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Movie addMovie(MediaList mediaList, MediaDetails details) {
        Movie movie = new Movie(mediaList, 1, details);
        return this.movieRepository.save(movie);
    }

    @Override
    public Movie removeMovie(MediaList mediaList, int index) {
        Movie movie = getMovie(mediaList, index);
        this.movieRepository.delete(movie);
        return movie;
    }

    @Override
    public Series getShow(MediaList mediaList, int index) {
        return this.seriesRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Series addShow(MediaList mediaList, MediaDetails details) {
        Series series = new Series(mediaList, 1, details);
        return this.seriesRepository.save(series);
    }

    @Override
    public Series removeShow(MediaList mediaList, int index) {
        Series series = getShow(mediaList, index);
        this.seriesRepository.delete(series);
        return series;
    }

    @Override
    public Book getBook(MediaList mediaList, int index) {
        return this.bookRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Book addBook(MediaList mediaList, MediaDetails details) {
        Book book = new Book(mediaList, 1, details);
        return this.bookRepository.save(book);
    }

    @Override
    public Book removeBook(MediaList mediaList, int index) {
        Book book = getBook(mediaList, index);
        this.bookRepository.delete(book);
        return book;
    }
}
