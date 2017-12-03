package com.mediarchive.server.web;

import com.mediarchive.server.domain.*;
import com.mediarchive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${api.key}")
    private String api_key;

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public @ResponseBody Object getUsers(@RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        List<User> users = userService.findAll();
        return users;
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public @ResponseBody Object getUser(@RequestParam("username") String username, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        User user = userService.getUser(username);
        if (user != null) {
            return user;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public @ResponseBody Object createUser(@RequestBody String body, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        User user = userService.addUser(body);
        if (user != null) {
            return HttpStatus.OK;
        }
        //user could not map properly or already exists
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getMovies", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getMovies(@RequestParam("username") String username, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        String movies = userService.getMovies(username);
        if (movies != null) {
            return movies;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getShows", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getSeries(@RequestParam("username") String username, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        String series = userService.getSeries(username);
        if (series != null) {
            return series;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getBooks", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getBooks(@RequestParam("username") String username, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        String books = userService.getBooks(username);
        if (books != null) {
            return books;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getStats", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getStats(@RequestParam("username") String username, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        String stats = userService.getStats(username);
        if (stats != null) {
            return stats;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody Object add(@RequestParam("list") String list, @RequestParam("media") String media, @RequestParam("username") String username, @RequestBody String body, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return HttpStatus.FORBIDDEN;
        Object toReturn = HttpStatus.BAD_REQUEST;
        if (media.equalsIgnoreCase("movie")) {
            if (list.equalsIgnoreCase("completed")) {
                toReturn = userService.addCompletedMovie(username, body);
            }
            else if (list.equalsIgnoreCase("planning")) {
                toReturn = userService.addIntentMovie(username, body);
            }
        }
        else if (media.equalsIgnoreCase("show")) {
            if (list.equalsIgnoreCase("completed")) {
                toReturn = userService.addCompletedSeries(username, body);
            }
            else if (list.equalsIgnoreCase("current")) {
                toReturn = userService.addUnderwaySeries(username, body);
            }
            else if (list.equalsIgnoreCase("planning")) {
                toReturn = userService.addIntentSeries(username, body);
            }
        }
        else if (media.equalsIgnoreCase("book")) {
            if (list.equalsIgnoreCase("completed")) {
                toReturn = userService.addCompletedBook(username, body);
            }
            else if (list.equalsIgnoreCase("current")) {
                toReturn = userService.addUnderwayBook(username, body);
            }
            else if (list.equalsIgnoreCase("planning")) {
                toReturn = userService.addIntentBook(username, body);
            }
        }
        //could not parse body into movie or username doesn't exist
        return toReturn;
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam("list") String list, @RequestParam("media") String media, @RequestParam String id, @RequestParam("username") String username, @RequestParam("key") String key) {
        if (!key.equals(api_key)) return;
        if (media.equalsIgnoreCase("movie")) {
            if (list.equalsIgnoreCase("completed")) {
                userService.removeCompletedMovie(username, id);
            }
            else if (list.equalsIgnoreCase("planning")) {
                userService.removeIntentMovie(username, id);
            }
        }
        else if (media.equalsIgnoreCase("show")) {
            if (list.equalsIgnoreCase("completed")) {
                userService.removeCompletedSeries(username, id);
            }
            else if (list.equalsIgnoreCase("current")) {
                userService.removeUnderwaySeries(username, id);
            }
            else if (list.equalsIgnoreCase("planning")) {
                userService.removeIntentSeries(username, id);
            }
        }
        else if (media.equalsIgnoreCase("book")) {
            if (list.equalsIgnoreCase("completed")) {
                userService.removeCompletedBook(username, id);
            }
            else if (list.equalsIgnoreCase("current")) {
                userService.removeUnderwayBook(username, id);
            }
            else if (list.equalsIgnoreCase("planning")) {
                userService.removeIntentBook(username, id);
            }
        }
    }

}
