package com.mediarchive.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    HttpEntity<PagedResources<User>> getAllUsers(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<User> users = repository.findAll(pageable);
        return new ResponseEntity<PagedResources<User>>(assembler.toResource(users), HttpStatus.OK);
    }

}
