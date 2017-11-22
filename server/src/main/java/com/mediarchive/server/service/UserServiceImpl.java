package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MediaListRepository mediaListRepository;

    public UserServiceImpl(UserRepository userRepository, MediaListRepository mediaListRepository) {
        this.userRepository = userRepository;
        this.mediaListRepository = mediaListRepository;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getUser(String name) {
        return this.userRepository.findByName(name);
    }

    @Override
    public User addUser(String name, String password) {
        User user = new User(name, password, 1);
        return this.userRepository.save(user);
    }

    @Override
    public Page<MediaList> getMediaList(User user, Pageable pageable) {
        return this.mediaListRepository.findByUser(user, pageable);
    }
}
