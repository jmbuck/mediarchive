package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> findAll(Pageable pageable);

    User getUser(String name);

    User addUser(String name, String password);

    Page<MediaList> getMediaList(User user, Pageable pageable);
}
