package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MediaListService mediaListService;

    private final MediaListRepository mediaListRepository;
    private final UserRepository userRepository;

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

    @Override
    public MediaList getMediaComplete(User user, Pageable pageable) {
        return user.getMediaCompleted();
    }

    @Override
    public Page<MediaList> getMediaUnderway(User user, Pageable pageable) {
        return this.mediaListRepository.findByUser_MediaUnderway(user, pageable);
    }

    @Override
    public Page<MediaList> getMediaIntent(User user, Pageable pageable) {
        return this.mediaListRepository.findByUser_MediaIntent(user, pageable);
    }

    @Override
    public Book addCompletedBook(User user, MediaDetails mediaDetails, Pageable pageable) {
       return mediaListService.addBook(user.getMediaCompleted(), mediaDetails);
    }

    @Override
    public Page<Book> getBooks(User user, Pageable pageable) {
        return mediaListService.getBooks(user.getMediaCompleted(), pageable);
    }
}
