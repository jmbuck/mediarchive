package com.mediarchive.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServerController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
