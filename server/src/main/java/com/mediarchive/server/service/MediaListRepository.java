package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MediaListRepository extends CrudRepository<MediaList, Long> {
    MediaList findByUser_MediaCompleted(User user);
    MediaList findByUser_MediaIntent(User user);
    MediaList findByUser_MediaUnderway(User user);
    <S extends MediaList>S save(S mediaList);
}