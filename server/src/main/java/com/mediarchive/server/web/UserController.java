package com.mediarchive.server.web;

import com.mediarchive.server.domain.User;
import com.mediarchive.server.service.MediaListService;
import com.mediarchive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MediaListService mediaListService;

    @Transactional
    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    public Object getAllUsers(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @Transactional
    @RequestMapping(value = "/postuser", method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestParam String username, @RequestParam String password) {
        return userService.addUser(username, password);
    }

    @Transactional
    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public Object getUser(@RequestParam String username) {
        return userService.getUser(username);
    }

    @RequestMapping(value = "/media", method = RequestMethod.GET)
    public Object getMedia() {
        return mediaListService;
    }
}
