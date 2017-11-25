package com.mediarchive.server.web;

import com.mediarchive.server.domain.*;
import com.mediarchive.server.service.MediaListService;
import com.mediarchive.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServlet;
import javax.transaction.Transactional;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/weebs", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllOfTheUsers(Pageable pageable) {
        Page<User> page = userService.findAll(pageable);
        List<User> users = page.getContent();
        for (User u: users) {
            System.out.println(u + ": " + userService.getBooks(u, pageable).getContent().get(0));
        }
        return page.getContent();
    }

    @RequestMapping(value = "createweeb", method = RequestMethod.POST)
    public @ResponseBody Object createWeeb(@RequestBody String body) {
        userService.addUser("hard test", "hard test");
        return HttpStatus.OK;
    }

    @RequestMapping(value = "addbook", method = RequestMethod.POST)
    public @ResponseBody Object addBook(@RequestBody String body, Pageable pageable) {
        String uid = "hard test";
        User user = userService.getUser(uid);
        System.out.println("Controller user: " + user);
        MediaDetails details = new MediaDetails();
        details.setId("ID");
        details.setTitle("TITLE");
        Book b = userService.addCompletedBook(user, details, pageable);
        System.out.println("Controller user completed: " + user.getMediaCompleted());
        return HttpStatus.OK;
    }
}
