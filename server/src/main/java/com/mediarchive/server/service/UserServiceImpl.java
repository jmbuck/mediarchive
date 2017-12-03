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
    public Movie addMovie(String username, String details) {
        return null;
    }

    @Override
    public Series addSeries(String username, String details) {
        return null;
    }

    @Override
    public Book addBook(String username, String details) {
        return null;
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


//
//    @Override
//    public Movie addCompletedMovie(String username, String body) {
//        Movie movie = null;
//        try {
//            MediaDetails details = objectMapper.readValue(body, MediaDetails.class);
//            User user = getUser(username);
//            if (user != null) {
//                movie = mediaListService.addMovie(user.getMediaCompleted(), details);
//                logger.info("Successfully added completed movie to user " + username);
//                return movie;
//            }
//        } catch (IOException e) {
//            logger.error("Could not parse JSON", e);
//        }
//        logger.error("Unsuccessful addition to user " + username + "'s completed movies");
//        return movie;
//    }
//
//    @Override
//    public List<Movie> getCompletedMovies(String username) {
//        User user = getUser(username);
//        if (user != null) {
//            logger.info("Request for user " + username + "'s completed movies list");
//            return mediaListService.getMovies(user.getMediaCompleted());
//        }
//        logger.error("Could not return user " + username + "'s completed movies list");
//        return null;
//    }
//
//    @Override
//    public Movie removeCompletedMovie(String username, String id) {
//        User user = getUser(username);
//        if (user != null) {
//            Movie movie = mediaListService.removeMovie(user.getMediaCompleted(), id);
//            if (movie != null) {
//                logger.info("Successfully removed completed movie from user " + username);
//                return movie;
//            }
//        }
//        logger.error("Unsuccessful removal to user " + username + "'s completed movies");
//        return null;
//    }

}
