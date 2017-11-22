package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component("mediaListService")
@Transactional
public class MediaListServiceImpl implements MediaListService {
    private final MediaListRepository mediaListRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final BookRepository bookRepository;

    public MediaListServiceImpl(MediaListRepository mediaListRepository,
                                MovieRepository movieRepository,
                                ShowRepository showRepository,
                                BookRepository bookRepository) {
        this.mediaListRepository = mediaListRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
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
    public Page<Show> getShows(MediaList mediaList, Pageable pageable) {
        return this.showRepository.findByMediaList(mediaList, pageable);
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
    public Show getShow(MediaList mediaList, int index) {
        return this.showRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Show addShow(MediaList mediaList, MediaDetails details) {
        Show show = new Show(mediaList, 1, details);
        return this.showRepository.save(show);
    }

    @Override
    public Show removeShow(MediaList mediaList, int index) {
        Show show = getShow(mediaList, index);
        this.showRepository.delete(show);
        return show;
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
