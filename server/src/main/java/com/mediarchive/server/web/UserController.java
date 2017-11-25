package com.mediarchive.server.web;

import com.mediarchive.server.domain.*;
import com.mediarchive.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public @ResponseBody Object getAllUsers() {
        logger.info("Request for all users");
        List<User> users = userService.findAll();
        String buffer = "";
        for (User u: users) {
            buffer += u.toString() + "\n";
        }
        return buffer;
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public @ResponseBody Object getUser(@RequestParam("name") String name) {
        User user = userService.getUser(name);
        if (user != null) {
            logger.info("Request for user " + name);
            return user.toString();
        }
        logger.error("Unsuccessful request for user " + name);
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public @ResponseBody Object createUser(@RequestBody String body) {
        String name = body.substring(body.indexOf("name") + 8, body.indexOf("password") - 5);
        String password = body.substring(body.indexOf("password") + 12, body.lastIndexOf("\""));

        if (!name.isEmpty() && userService.getUser(name) == null) {
            userService.addUser(name, password);
            logger.info("Successfully added user " + name);
            return HttpStatus.OK;
        }
        logger.error("Unsuccessful creation of user " + name);
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getCompletedList", method = RequestMethod.GET)
    public @ResponseBody Object getCompletedList(@RequestParam("name") String name) {
        User user = userService.getUser(name);
        if (user != null) {
            logger.info("Request for user " + name + "'s completed list");
            return user.getMediaCompleted();
        }
        logger.error("Could not return user " + name + "'s completed list");
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getUnderwayList", method = RequestMethod.GET)
    public @ResponseBody Object getUnderwayList(@RequestParam("name") String name) {
        User user = userService.getUser(name);
        if (user != null) {
            logger.info("Request for user " + name + "'s Underway list");
            return user.getMediaUnderway();
        }
        logger.error("Could not return user " + name + "'s Underway list");
        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "getIntentList", method = RequestMethod.GET)
    public @ResponseBody Object getIntentList(@RequestParam("name") String name) {
        User user = userService.getUser(name);
        if (user != null) {
            logger.info("Request for user " + name + "'s Intent list");
            return user.getMediaIntent();
        }
        logger.error("Could not return user " + name + "'s Intent list");
        return HttpStatus.BAD_REQUEST;
    }

}
