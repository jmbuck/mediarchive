package com.mediarchive.server.web;

import com.mediarchive.server.domain.MediaDetails;
import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Series;
import com.mediarchive.server.domain.User;
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
import javax.servlet.http.HttpServlet;
import javax.transaction.Transactional;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MediaListService mediaListService;

    @RequestMapping(value = "/weebs", method = RequestMethod.GET)
    public @ResponseBody List<User> getAllOfTheUsers(Pageable pageable) {
        Page<User> page = userService.findAll(pageable);
        List<User> users = page.getContent();
        for (User u: users) {
            System.out.println(userService.getMediaList(u, pageable));
        }
        return page.getContent();
    }

    @RequestMapping(value = "createweeb", method = RequestMethod.POST)
    public @ResponseBody Object createWeeb(@RequestBody String body) {
        userService.addUser("hard test", "hard test");
        return HttpStatus.OK;
    }

    @RequestMapping(value = "addanime", method = RequestMethod.POST)
    public @ResponseBody Object addAnime(@RequestBody String body, Pageable pageable) {
        String uid = "hard test";
        User user = userService.getUser(uid);
        System.out.println(user);
        MediaList ml = userService.getMediaComplete(user, pageable).getContent().get(0);
        System.out.println(ml);
        mediaListService.addShow(ml, new MediaDetails());
        return HttpStatus.OK;
    }
}
