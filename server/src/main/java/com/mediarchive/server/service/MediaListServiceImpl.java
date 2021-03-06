package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import java.util.List;

@Component("mediaListService")
@Transactional
public class MediaListServiceImpl implements MediaListService {

    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;
    private final BookRepository bookRepository;
    private final StatisticsRepository statisticsRepository;

    public MediaListServiceImpl(MovieRepository movieRepository,
                                SeriesRepository seriesRepository,
                                BookRepository bookRepository,
                                StatisticsRepository statisticsRepository) {
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
    public List<Book> getBooks(MediaList mediaList) {
        return this.bookRepository.findByMediaList(mediaList);
    }

    @Override
    public Book getBook(MediaList mediaList, String id) {
        return this.bookRepository.findByMediaListAndId(mediaList, id);
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
    public Movie addMovie(MediaList mediaList, MediaDetails details) {
        Movie movie = new Movie(mediaList, details);
        return addMovie(mediaList, movie);
    }
    
    @Override
    public Series addSeries(MediaList mediaList, MediaDetails details) {
        Series series = new Series(mediaList, details);
        return addSeries(mediaList, series);
    }
    
    @Override
    public Book addBook(MediaList mediaList, MediaDetails details) {
        Book book = new Book(mediaList, details);
        return addBook(mediaList, book);
    }

    @Override
    public Movie addMovie(MediaList mediaList, Movie movie) {
        Movie inRepo = movieRepository.findByMediaListAndId(mediaList, movie.getId());
        if (inRepo != null) {
            removeMovie(mediaList, inRepo.getId());
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalMovies(1);
        if (movie.getRuntime() > 0) {
            s.updateTotalMovieRuntime(movie.getRuntime());
            s.updateTotalMovieRuntimeCount(1);
        }
        if (movie.getScore() > 0) {
            s.updateTotalMovieScore(movie.getScore());
            s.updateTotalMovieScoreCount(1);
        }
        this.statisticsRepository.save(s);
        return this.movieRepository.save(movie);
    }

    @Override
    public Movie removeMovie(MediaList mediaList, String id) {
        Movie movie = getMovie(mediaList, id);
        if (movie != null) {
            Statistics s = this.statisticsRepository.findByMediaList(mediaList);
            s.updateTotalMovies(-1);
            if (movie.getRuntime() > 0) {
                s.updateTotalMovieRuntime(-movie.getRuntime());
                s.updateTotalMovieRuntimeCount(-1);
            }
            if (movie.getScore() > 0) {
                s.updateTotalMovieScore(-movie.getScore());
                s.updateTotalMovieScoreCount(-1);
            }
            this.statisticsRepository.save(s);
            this.movieRepository.delete(movie);
        }
        return movie;
    }

    @Override
    public Series addSeries(MediaList mediaList, Series series) {
        Series inRepo = seriesRepository.findByMediaListAndId(mediaList, series.getId());
        if (inRepo != null) {
            removeSeries(mediaList, inRepo.getId());
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalShows(1);
        if (series.getScore() > 0) {
            s.updateTotalShowScoreCount(1);
            s.updateTotalShowScore(series.getScore());
        }
        s.updateTotalEpisodes(series.getEpisodes_watched());
        s.updateTotalSeasons(series.getSeasons_watched());
        if (series.getEpisode_runtime() > 0) {
            s.updateTotalShowRuntime(series.getEpisode_runtime() * series.getEpisodes_watched());
            s.updateTotalShowRuntimeCount(series.getEpisodes_watched());
        }
        this.statisticsRepository.save(s);
        return this.seriesRepository.save(series);
    }

    @Override
    public Series removeSeries(MediaList mediaList, String id) {
        Series series = getSeries(mediaList, id);
        if (series != null) {
            Statistics s = this.statisticsRepository.findByMediaList(mediaList);
            s.updateTotalShows(-1);
            if (series.getScore() > 0) {
                s.updateTotalShowScoreCount(-1);
                s.updateTotalShowScore(-series.getScore());
            }
            s.updateTotalEpisodes(-series.getEpisodes_watched());
            s.updateTotalSeasons(-series.getSeasons_watched());
            if (series.getEpisode_runtime() > 0) {
                s.updateTotalShowRuntime(-series.getEpisode_runtime() * series.getEpisodes_watched());
                s.updateTotalShowRuntimeCount(-series.getEpisodes_watched());
            }
            this.statisticsRepository.save(s);
            this.seriesRepository.delete(series);
        }
        return series;
    }

    @Override
    public Book addBook(MediaList mediaList,Book book) {
        Book inRepo = bookRepository.findByMediaListAndId(mediaList, book.getId());
        System.out.println("AddBook: mediaList: " + mediaList + " | inRepo: " + inRepo);
        if (inRepo != null) {
            removeBook(mediaList, inRepo.getId());
        }
        Statistics s = this.statisticsRepository.findByMediaList(mediaList);
        s.updateTotalBooks(1);
        if (book.getScore() > 0) {
            s.updateTotalBookScore(book.getScore());
            s.updateTotalBookScoreCount(1);
        }
        if (book.getPage_count() > 0) {
            s.updateTotalPages(book.getPage_count());
            s.updateTotalPagesCount(1);
        }
        this.statisticsRepository.save(s);
        Book save = this.bookRepository.save(book);
        return save;
    }

    @Override
    public Book removeBook(MediaList mediaList, String id) {
        Book book = getBook(mediaList, id);
        System.out.println("Remove Book");
        System.out.println("Remove MediaList: " + mediaList + " | " + getBooks(mediaList).toString());
        if (book != null) {
            Statistics s = this.statisticsRepository.findByMediaList(mediaList);
            s.updateTotalBooks(-1);
            if (book.getScore() > 0) {
                s.updateTotalBookScore(-book.getScore());
                s.updateTotalBookScoreCount(-1);
            }
            if (book.getPage_count() > 0) {
                s.updateTotalPages(-book.getPage_count());
                s.updateTotalPagesCount(-1);
            }
            this.statisticsRepository.save(s);
            this.bookRepository.delete(book);
        }
        System.out.println("Remove MediaList: " + mediaList + " | " + getBooks(mediaList).toString());
        return book;
    }
}
