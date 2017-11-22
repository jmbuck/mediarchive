package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Page<Movie> findByMediaList(MediaList mediaList, Pageable pageable);
    Movie findByMediaListAndIndex(MediaList mediaList, int index);
    Movie save(Movie movie);
}
