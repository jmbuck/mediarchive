package com.mediarchive.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediarchive.server.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final MediaListService mediaListService;
    private final UserRepository userRepository;

    private ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, MediaListService mediaListService) {
        this.userRepository = userRepository;
        this.mediaListService = mediaListService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<User> findAll() {
        logger.info("Request for all users");
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            logger.info("Successful request for user " + username);
        }
        else {
            logger.info("Could not find user " + username);
        }
        return user;
    }

    @Override
    public User getUser(String username, String password) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User addUser(String body) {
        User user = null;
        try {
            user = objectMapper.readValue(body, User.class);
            if (this.userRepository.findByUsername(user.getUsername()) == null) {
                this.userRepository.save(user);
                logger.info("Successfully created new user " + user.getUsername());
            }
            else {
                logger.error("User already exists");
                user = null;
            }
        } catch (IOException e) {
            logger.error("Unsuccessful creation of new user");
        }
        return user;
    }

    @Override
    public User addUser(String username, String password) {
        User user = null;
        if (this.userRepository.findByUsername(username) == null) {
            user = new User(username, password);
            this.userRepository.save(user);
        }
        return user;
    }

    @Override
    public String getMovies(String username) {
        User user = getUser(username);
        if (user != null) {
            List<Movie> completed = this.mediaListService.getMovies(user.getMediaCompleted());
            List<Movie> intent = this.mediaListService.getMovies(user.getMediaIntent());
            String toReturn =  null;
            try {
                toReturn =  "{\n\"completed\": " + objectMapper.writeValueAsString(completed) +
                            ",\n\"planning\": " + objectMapper.writeValueAsString(intent) + "\n}";
            } catch (Exception e) {
                logger.error("Could not write movies for user " + username, e);
            }
            return toReturn;
        }
        logger.error("Unsuccessful movies request for user " + username);
        return null;
    }

    @Override
    public String getSeries(String username) {
        User user = getUser(username);
        if (user != null) {
            List<Series> completed = this.mediaListService.getSeries(user.getMediaCompleted());
            List<Series> underway = this.mediaListService.getSeries(user.getMediaUnderway());
            List<Series> intent = this.mediaListService.getSeries(user.getMediaIntent());
            String toReturn =  null;
            try {
                toReturn =  "{\n\"completed\": " + objectMapper.writeValueAsString(completed) +
                        ",\n\"current\": " + objectMapper.writeValueAsString(underway)  +
                        ",\n\"planning\": " + objectMapper.writeValueAsString(intent) + "\n}";
            } catch (Exception e) {
                logger.error("Could not write series for user " + username, e);
            }
            return toReturn;
        }
        logger.error("Unsuccessful series request for user " + username);
        return null;
    }

    @Override
    public String getBooks(String username) {
        User user = getUser(username);
        if (user != null) {
            List<Book> completed = this.mediaListService.getBooks(user.getMediaCompleted());
            List<Book> underway = this.mediaListService.getBooks(user.getMediaUnderway());
            List<Book> intent = this.mediaListService.getBooks(user.getMediaIntent());
            String toReturn =  null;
            try {
                toReturn =  "{\n\"completed\": " + objectMapper.writeValueAsString(completed) +
                        ",\n\"current\": " + objectMapper.writeValueAsString(underway)  +
                        ",\n\"planning\": " + objectMapper.writeValueAsString(intent) + "\n}";
            } catch (Exception e) {
                logger.error("Could not write books for user " + username, e);
            }
            return toReturn;
        }
        logger.error("Unsuccessful books request for user " + username);
        return null;
    }

    @Override
    public String getStats(String username) {
        User user = getUser(username);
        if (user != null) {
            Statistics completed = this.mediaListService.getStatistics(user.getMediaCompleted());
            Statistics underway = this.mediaListService.getStatistics(user.getMediaUnderway());
            Statistics intent = this.mediaListService.getStatistics(user.getMediaIntent());
            String toReturn =  null;
            try {
                toReturn =  "{\n\"completed\": " + objectMapper.writeValueAsString(completed) +
                            ",\n\"current\": " + objectMapper.writeValueAsString(underway)  +
                            ",\n\"planning\": " + objectMapper.writeValueAsString(intent) + "\n}";
            } catch (Exception e) {
                logger.error("Could not write statistics for user " + username, e);
            }
            return toReturn;

        }
        logger.error("Unsuccessful statistics request for user " + username);
        return null;
    }

    @Override
    public Movie addCompletedMovie(String username, String body) {
        Movie movie = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                movie = mediaListService.addMovie(user.getMediaCompleted(), details);
                logger.info("Successfully added completed movie to user " + username);
                return movie;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s completed movies");
        return movie;
    }

    @Override
    public Series addCompletedSeries(String username, String body) {
        System.out.println(body);
        Series series = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                series = mediaListService.addSeries(user.getMediaCompleted(), details);
                logger.info("Successfully added completed series to user " + username);
                return series;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s completed series");
        return series;
    }

    @Override
    public Book addCompletedBook(String username, String body) {
        Book book = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                book = mediaListService.addBook(user.getMediaCompleted(), details);
                logger.info("Successfully added completed book to user " + username);
                return book;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s completed books");
        return book;
    }

    @Override
    public Movie removeCompletedMovie(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Movie movie = mediaListService.removeMovie(user.getMediaCompleted(), id);
            if (movie != null) {
                logger.info("Successfully removed completed movie from user " + username);
                return movie;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s completed movies");
        return null;
    }

    @Override
    public Series removeCompletedSeries(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Series series = mediaListService.removeSeries(user.getMediaCompleted(), id);
            if (series != null) {
                logger.info("Successfully removed completed series from user " + username);
                return series;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s completed series");
        return null;
    }

    @Override
    public Book removeCompletedBook(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Book book = mediaListService.removeBook(user.getMediaCompleted(), id);
            if (book != null) {
                logger.info("Successfully removed completed book from user " + username);
                return book;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s completed books");
        return null;
    }

    @Override
    public Series addUnderwaySeries(String username, String body) {
        Series series = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                series = mediaListService.addSeries(user.getMediaUnderway(), details);
                logger.info("Successfully added underway series to user " + username);
                return series;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s underway series");
        return series;
    }

    @Override
    public Book addUnderwayBook(String username, String body) {
        Book book = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                book = mediaListService.addBook(user.getMediaUnderway(), details);
                logger.info("Successfully added underway book to user " + username);
                return book;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s underway books");
        return book;
    }

    @Override
    public Series removeUnderwaySeries(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Series series = mediaListService.removeSeries(user.getMediaUnderway(), id);
            if (series != null) {
                logger.info("Successfully removed underway series from user " + username);
                return series;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s underway series");
        return null;
    }

    @Override
    public Book removeUnderwayBook(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Book book = mediaListService.removeBook(user.getMediaUnderway(), id);
            if (book != null) {
                logger.info("Successfully removed underway book from user " + username);
                return book;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s underway books");
        return null;
    }

    @Override
    public Movie addIntentMovie(String username, String body) {
        Movie movie = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                movie = mediaListService.addMovie(user.getMediaIntent(), details);
                logger.info("Successfully added intent movie to user " + username);
                return movie;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s intent movies");
        return movie;
    }

    @Override
    public Series addIntentSeries(String username, String body) {
        Series series = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                series = mediaListService.addSeries(user.getMediaIntent(), details);
                logger.info("Successfully added intent series to user " + username);
                return series;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s intent series");
        return series;
    }

    @Override
    public Book addIntentBook(String username, String body) {
        Book book = null;
        try {
            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
            User user = getUser(username);
            if (user != null) {
                book = mediaListService.addBook(user.getMediaIntent(), details);
                logger.info("Successfully added intent book to user " + username);
                return book;
            }
        } catch (IOException e) {
            logger.error("Could not parse JSON", e);
        }
        logger.error("Unsuccessful addition to user " + username + "'s intent books");
        return book;
    }

    @Override
    public Movie removeIntentMovie(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Movie movie = mediaListService.removeMovie(user.getMediaIntent(), id);
            if (movie != null) {
                logger.info("Successfully removed intent movie from user " + username);
                return movie;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s intent movies");
        return null;
    }

    @Override
    public Series removeIntentSeries(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Series series = mediaListService.removeSeries(user.getMediaIntent(), id);
            if (series != null) {
                logger.info("Successfully removed intent series from user " + username);
                return series;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s intent series");
        return null;
    }

    @Override
    public Book removeIntentBook(String username, String id) {
        User user = getUser(username);
        if (user != null) {
            Book book = mediaListService.removeBook(user.getMediaIntent(), id);
            if (book != null) {
                logger.info("Successfully removed intent book from user " + username);
                return book;
            }
        }
        logger.error("Unsuccessful removal to user " + username + "'s intent books");
        return null;
    }
}
