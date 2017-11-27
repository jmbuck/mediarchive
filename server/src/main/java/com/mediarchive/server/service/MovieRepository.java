package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findByMediaList(MediaList mediaList);
    Movie findByMediaListAndIndex(MediaList mediaList, int index);
    Movie findByMediaListAndId(MediaList mediaList, String id);
    <S extends Movie>S save(S movie);
}
