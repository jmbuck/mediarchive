package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component("mediaListService")
@Transactional
public class MediaListServiceImpl implements MediaListService {

    private final MediaListRepository mediaListRepository;
    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;
    private final BookRepository bookRepository;
    private final StatisticsRepository statisticsRepository;

    public MediaListServiceImpl(MediaListRepository mediaListRepository,
                                MovieRepository movieRepository,
                                SeriesRepository seriesRepository,
                                BookRepository bookRepository,
                                StatisticsRepository statisticsRepository) {
        this.mediaListRepository = mediaListRepository;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.bookRepository = bookRepository;
        this.statisticsRepository = statisticsRepository;
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
    public Series getSeries(MediaList mediaList, String id) {
        return this.seriesRepository.findByMediaListAndId(mediaList, id);
    }

    @Override
    public Series getSeries(MediaList mediaList, int index) {
        return this.seriesRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public List<Book> getBooks(MediaList mediaList) {
        return this.bookRepository.findByMediaList(mediaList);
    }

    @Override
    public Book getBook(MediaList mediaList, String id) {
        return this.bookRepository.findByMediaListAndId(mediaList, id);
    }

    @Override
    public Book getBook(MediaList mediaList, int index) {
        return this.bookRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Statistics getStatistics(MediaList mediaList) {
        Statistics stats = this.statisticsRepository.findByMediaList(mediaList);
        stats.updateMeans();
        statisticsRepository.save(stats);
        return stats;
    }

    @Override
    public Movie getMovie(MediaList mediaList, String id) {
        return this.movieRepository.findByMediaListAndId(mediaList, id);
    }

    @Override
    public Movie getMovie(MediaList mediaList, int index) {
        return this.movieRepository.findByMediaListAndIndex(mediaList, index);
    }

    @Override
    public Movie addMovie(MediaList mediaList, MediaDetails details) {
        Movie movie = new Movie(mediaList, 1, details);
        Movie inRepo = movieRepository.findByMediaListAndId(mediaList, movie.getId());
        if (inRepo != null) {
            removeMovie(mediaList, inRepo.getId());
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalMovies(1);
        s.updateTotalMovieRuntime(movie.getRuntime());
        s.updateTotalMovieScore(movie.getScore());
        this.statisticsRepository.save(s);
        return this.movieRepository.save(movie);
    }

    @Override
    public Movie removeMovie(MediaList mediaList, String id) {
        Movie movie = getMovie(mediaList, id);
        if (movie == null) {
            return movie;
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalMovies(-1);
        s.updateTotalMovieRuntime(-movie.getRuntime());
        s.updateTotalMovieScore(-movie.getScore());
        this.statisticsRepository.save(s);
        this.movieRepository.delete(movie);
        return movie;
    }

    @Override
    public Series addSeries(MediaList mediaList, MediaDetails details) {
        Series series = new Series(mediaList, 1,details);
        Series inRepo = seriesRepository.findByMediaListAndId(mediaList, series.getId());
        if (inRepo != null) {
            removeSeries(mediaList, inRepo.getId());
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalShows(1);
        s.updateTotalShowScore(series.getScore());
        s.updateTotalEpisodes(series.getEpisodes_watched());
        s.updateTotalSeasons(series.getSeasons_watched());
        s.updateTotalShowRuntime(series.getEpisode_runtime() * series.getEpisodes_watched());
        this.statisticsRepository.save(s);
        return this.seriesRepository.save(series);
    }

    @Override
    public Series removeSeries(MediaList mediaList, String id) {
        Series series = getSeries(mediaList, id);
        if (series == null) {
            return series;
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalShows(-1);
        s.updateTotalShowScore(-series.getScore());
        s.updateTotalEpisodes(-series.getEpisodes_watched());
        s.updateTotalSeasons(-series.getSeasons_watched());
        s.updateTotalShowRuntime(-series.getEpisode_runtime() * series.getEpisodes_watched());
        this.statisticsRepository.save(s);
        this.seriesRepository.delete(series);
        return series;
    }

    @Override
    public Book addBook(MediaList mediaList, MediaDetails details) {
        Book book = new Book(mediaList, 1, details);
        Book inRepo = bookRepository.findByMediaListAndId(mediaList, book.getId());
        if (inRepo != null) {
            removeBook(mediaList, inRepo.getId());
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalBooks(1);
        s.updateTotalBookScore(book.getScore());
        s.updateTotalPages(book.getPage_count());
        this.statisticsRepository.save(s);
        return this.bookRepository.save(book);
    }

    @Override
    public Book removeBook(MediaList mediaList, String id) {
        Book book = getBook(mediaList, id);
        if (book == null) {
            return book;
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalBooks(-1);
        s.updateTotalBookScore(-book.getScore());
        s.updateTotalPages(-book.getPage_count());
        this.statisticsRepository.save(s);
        this.bookRepository.delete(book);
        return book;
    }
}
