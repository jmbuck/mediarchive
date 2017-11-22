package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

interface MediaListRepository extends CrudRepository<MediaList, Long> {

    Page<MediaList> findByUser(User user, Pageable pageable);

}
