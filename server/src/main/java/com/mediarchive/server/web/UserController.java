package com.mediarchive.server.web;

import com.mediarchive.server.domain.*;
import com.mediarchive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public @ResponseBody Object getUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public @ResponseBody Object getUser(@RequestParam("username") String username) {
        User user = userService.getUser(username);
        if (user != null) {
            return user;
        }
        return HttpStatus.BAD_REQUEST;
    }
    
    @RequestMapping(value = "getCompletedStatistics", method = RequestMethod.GET)
    public @ResponseBody Object getCompletedStatistics(@RequestParam("username") String username) {
        Statistics stats = userService.getCompletedStatistics(username);
        if (stats != null) {
            return stats;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getUnderwayStatistics", method = RequestMethod.GET)
    public @ResponseBody Object getUnderwayStatistics(@RequestParam("username") String username) {
        Statistics stats = userService.getUnderwayStatistics(username);
        if (stats != null) {
            return stats;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getIntentStatistics", method = RequestMethod.GET)
    public @ResponseBody Object getIntentStatistics(@RequestParam("username") String username) {
        Statistics stats = userService.getIntentStatistics(username);
        if (stats != null) {
            return stats;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public @ResponseBody Object createUser(@RequestBody String body) {
        User user = userService.addUser(body);
        if (user != null) {
            return HttpStatus.OK;
        }
        //user could not map properly or already exists
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getCompletedBooks", method = RequestMethod.GET)
    public @ResponseBody Object getCompletedBooks(@RequestParam("username") String username) {
        List<Book> books = userService.getCompletedBooks(username);
        if (books != null) {
            return books;
        }
        //books does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addCompletedBook", method = RequestMethod.POST)
    public @ResponseBody Object addCompletedBook(@RequestParam("username") String username, @RequestBody String body) {
        Book book = userService.addCompletedBook(username, body);
        if (book != null) {
            return book;
        }
        //could not parse body into book or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getCompletedMovies", method = RequestMethod.GET)
    public @ResponseBody Object getCompletedMovies(@RequestParam("username") String username) {
        List<Movie> movies = userService.getCompletedMovies(username);
        if (movies != null) {
            return movies;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addCompletedMovie", method = RequestMethod.POST)
    public @ResponseBody Object addCompletedMovie(@RequestParam("username") String username, @RequestBody String body) {
        Movie movie = userService.addCompletedMovie(username, body);
        if (movie != null) {
            return movie;
        }
        //could not parse body into movie or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getCompletedShow", method = RequestMethod.GET)
    public @ResponseBody Object getCompletedShow(@RequestParam("username") String username) {
        List<Series> seriess = userService.getCompletedSeries(username);
        if (seriess != null) {
            return seriess;
        }
        //seriess does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addCompletedShow", method = RequestMethod.POST)
    public @ResponseBody Object addCompletedShow(@RequestParam("username") String username, @RequestBody String body) {
        Series series = userService.addCompletedSeries(username, body);
        if (series != null) {
            return series;
        }
        //could not parse body into series or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "deleteCompletedBook", method = RequestMethod.DELETE)
    public void deleteCompletedBook(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeCompletedBook(username, id);
    }

    @RequestMapping(value = "deleteCompletedShow", method = RequestMethod.DELETE)
    public void deleteCompletedShow(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeCompletedSeries(username, id);
    }

    @RequestMapping(value = "deleteCompletedMovie", method = RequestMethod.DELETE)
    public void deleteCompletedMovie(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeCompletedMovie(username, id);
    }
    
    @RequestMapping(value = "getUnderwayBooks", method = RequestMethod.GET)
    public @ResponseBody Object getUnderwayBooks(@RequestParam("username") String username) {
        List<Book> books = userService.getUnderwayBooks(username);
        if (books != null) {
            return books;
        }
        //books does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addUnderwayBook", method = RequestMethod.POST)
    public @ResponseBody Object addUnderwayBook(@RequestParam("username") String username, @RequestBody String body) {
        Book book = userService.addUnderwayBook(username, body);
        if (book != null) {
            return book;
        }
        //could not parse body into book or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getUnderwayMovies", method = RequestMethod.GET)
    public @ResponseBody Object getUnderwayMovies(@RequestParam("username") String username) {
        List<Movie> movies = userService.getUnderwayMovies(username);
        if (movies != null) {
            return movies;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addUnderwayMovie", method = RequestMethod.POST)
    public @ResponseBody Object addUnderwayMovie(@RequestParam("username") String username, @RequestBody String body) {
        Movie movie = userService.addUnderwayMovie(username, body);
        if (movie != null) {
            return movie;
        }
        //could not parse body into movie or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getUnderwayShow", method = RequestMethod.GET)
    public @ResponseBody Object getUnderwayShow(@RequestParam("username") String username) {
        List<Series> seriess = userService.getUnderwaySeries(username);
        if (seriess != null) {
            return seriess;
        }
        //seriess does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addUnderwayShow", method = RequestMethod.POST)
    public @ResponseBody Object addUnderwayShow(@RequestParam("username") String username, @RequestBody String body) {
        Series series = userService.addUnderwaySeries(username, body);
        if (series != null) {
            return series;
        }
        //could not parse body into series or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "deleteUnderwayBook", method = RequestMethod.DELETE)
    public void deleteUnderwayBook(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeUnderwayBook(username, id);
    }

    @RequestMapping(value = "deleteUnderwayShow", method = RequestMethod.DELETE)
    public void deleteUnderwayShow(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeUnderwaySeries(username, id);
    }

    @RequestMapping(value = "deleteUnderwayMovie", method = RequestMethod.DELETE)
    public void deleteUnderwayMovie(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeUnderwayMovie(username, id);
    }

    @RequestMapping(value = "getIntentBooks", method = RequestMethod.GET)
    public @ResponseBody Object getIntentBooks(@RequestParam("username") String username) {
        List<Book> books = userService.getIntentBooks(username);
        if (books != null) {
            return books;
        }
        //books does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addIntentBook", method = RequestMethod.POST)
    public @ResponseBody Object addIntentBook(@RequestParam("username") String username, @RequestBody String body) {
        Book book = userService.addIntentBook(username, body);
        if (book != null) {
            return book;
        }
        //could not parse body into book or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getIntentMovies", method = RequestMethod.GET)
    public @ResponseBody Object getIntentMovies(@RequestParam("username") String username) {
        List<Movie> movies = userService.getIntentMovies(username);
        if (movies != null) {
            return movies;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addIntentMovie", method = RequestMethod.POST)
    public @ResponseBody Object addIntentMovie(@RequestParam("username") String username, @RequestBody String body) {
        Movie movie = userService.addIntentMovie(username, body);
        if (movie != null) {
            return movie;
        }
        //could not parse body into movie or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getIntentShows", method = RequestMethod.GET)
    public @ResponseBody Object getIntentShows(@RequestParam("username") String username) {
        List<Series> seriess = userService.getIntentSeries(username);
        if (seriess != null) {
            return seriess;
        }
        //seriess does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addIntentShow", method = RequestMethod.POST)
    public @ResponseBody Object addIntentShow(@RequestParam("username") String username, @RequestBody String body) {
        Series series = userService.addIntentSeries(username, body);
        if (series != null) {
            return series;
        }
        //could not parse body into series or username doesn't exist
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "deleteIntentBook", method = RequestMethod.DELETE)
    public void deleteIntentBook(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeIntentBook(username, id);
    }

    @RequestMapping(value = "deleteIntentShow", method = RequestMethod.DELETE)
    public void deleteIntentShow(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeIntentSeries(username, id);
    }

    @RequestMapping(value = "deleteIntentMovie", method = RequestMethod.DELETE)
    public void deleteIntentMovie(@RequestParam("username") String username, @RequestParam String id) {
        userService.removeIntentMovie(username, id);
    }

}
