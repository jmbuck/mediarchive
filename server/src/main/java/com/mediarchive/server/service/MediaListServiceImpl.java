package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component("mediaListService")
@Transactional
public class MediaListServiceImpl implements MediaListService {

    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;
    private final BookRepository bookRepository;

    public MediaListServiceImpl(MovieRepository movieRepository,
                                SeriesRepository seriesRepository,
                                BookRepository bookRepository) {
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Movie> getMovies(MediaList mediaList) {
        return this.movieRepository.findByMediaList(mediaList);
    }

    @Override
    public List<Series> getSeries(MediaList mediaList) {
        return this.seriesRepository.findByMediaList(mediaList);
    }

    @Override
    public List<Book> getBooks(MediaList mediaList) {
        return this.bookRepository.findByMediaList(mediaList);
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
    public Series getSeries(MediaList mediaList, int index) {
        return this.seriesRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Series addSeries(MediaList mediaList, MediaDetails details) {
        Series series = new Series(mediaList, 1, details);
        return this.seriesRepository.save(series);
    }

    @Override
    public Series removeSeries(MediaList mediaList, int index) {
        Series series = getSeries(mediaList, index);
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
