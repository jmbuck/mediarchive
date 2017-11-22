package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ShowRepository extends CrudRepository<Show, Long> {
    Page<Show> findByMediaList(MediaList mediaList, Pageable pageable);
    Show findByMediaListAndIndex(MediaList mediaList, int index);
    Show save(Show movie);
}
