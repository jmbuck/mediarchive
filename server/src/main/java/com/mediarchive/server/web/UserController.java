package com.mediarchive.server.web;

import com.mediarchive.server.domain.*;
import com.mediarchive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${spring.api.key}")
    private String api_key;

    @RequestMapping(value = "getUsers", method = RequestMethod.GET)
    public @ResponseBody Object getUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public @ResponseBody Object getUser(@RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        User user = userService.getUser(username);
        if (user != null) {
            return user;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getMovies", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getMovies(@RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        String movies = userService.getMovies(username);
        if (movies != null) {
            return movies;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getShows", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getSeries(@RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        String series = userService.getSeries(username);
        if (series != null) {
            return series;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getBooks", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getBooks(@RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        String books = userService.getBooks(username);
        if (books != null) {
            return books;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getStats", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Object getStats(@RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        String stats = userService.getStats(username);
        if (stats != null) {
            return stats;
        }
        //movies does not exist or bad username
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public @ResponseBody Object addMedia(@RequestParam("list") String list, @RequestParam("media") String media, @RequestParam("username") String username, @RequestBody String body, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
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
                System.out.println(toReturn);
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

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public @ResponseBody Object updateMedia(@RequestParam("oldlist") String oldlist, @RequestParam("newlist") String newlist, @RequestParam("media") String media, @RequestParam("id") String id, @RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers){
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        Object toReturn = null;
        if (media.equalsIgnoreCase("movie")) {
            toReturn = userService.updateMovie(username, oldlist, newlist, id);
        }
        else if (media.equalsIgnoreCase("show")) {
            toReturn = userService.updateSeries(username, oldlist, newlist, id);
        }
        else if (media.equalsIgnoreCase("book")) {
            System.out.println("In controller");
            toReturn = userService.updateBook(username, oldlist, newlist, id);
        }
        if (toReturn != null) {
            return toReturn;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public @ResponseBody Object deleteMedia(@RequestParam("list") String list, @RequestParam("media") String media, @RequestParam("id") String id, @RequestParam("username") String username, @RequestParam("key") String key, @RequestHeader HttpHeaders headers) {
        if (!checkKey(key) || !checkCredentials(username, headers.getFirst("Authorization"))) return HttpStatus.FORBIDDEN;
        if (media.equalsIgnoreCase("movie")) {
            if (list.equalsIgnoreCase("completed")) {
                return userService.removeCompletedMovie(username, id);
            }
            else if (list.equalsIgnoreCase("planning")) {
                return userService.removeIntentMovie(username, id);
            }
        }
        else if (media.equalsIgnoreCase("show")) {
            if (list.equalsIgnoreCase("completed")) {
                return userService.removeCompletedSeries(username, id);
            }
            else if (list.equalsIgnoreCase("current")) {
                return userService.removeUnderwaySeries(username, id);
            }
            else if (list.equalsIgnoreCase("planning")) {
                return userService.removeIntentSeries(username, id);
            }
        }
        else if (media.equalsIgnoreCase("book")) {
            if (list.equalsIgnoreCase("completed")) {
                return userService.removeCompletedBook(username, id);
            }
            else if (list.equalsIgnoreCase("current")) {
                return userService.removeUnderwayBook(username, id);
            }
            else if (list.equalsIgnoreCase("planning")) {
                return userService.removeIntentBook(username, id);
            }
        }
        return HttpStatus.OK;
    }

    @RequestMapping(value = "getHelp", method = RequestMethod.GET)
    public @ResponseBody String getHelp() {
        return  "The following are endpoints: \n" +
                "getUser?username={username}&key={api-key}\n" +
                "getStats?username={username}&key={api-key}\n" +
                "getMovies?username={username}&key={api-key}\n" +
                "getShows?username={username}&key={api-key}\n" +
                "getBooks?username={username}&key={api-key}\n" +
                "addUser?key={api-key}\n" +
                "add?list={completed, current, planning}&media={movie, show, book}&username={user}&key={api-key}\n" +
                "delete?id={media-id}&list={completed, current, planning}&media={movie, show, book}&username={user}&key={api-key}\n" +
                "logout";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public @ResponseBody Object login(@RequestHeader HttpHeaders headers, @RequestParam("username") String username) {
        boolean b = checkCredentials(username, headers.getFirst("Authorization"));
        if (b) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public @ResponseBody Object addUser(@RequestBody String body, @RequestParam("key") String key) {
        User user = null;
        if (checkKey(key)) {
            user = userService.addUser(body);
        }
        return user;
    }

    private boolean checkCredentials(String username, String password) {
        User user = userService.getUser(username, password);
        if (user != null) {
            return true;
        }
        return false;
    }

    private boolean checkKey(String key) {
        if (key.equals(api_key)) {
            return true;
        }
        return false;
    }
}
