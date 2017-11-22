package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<Series, Long> {
    Page<Series> findByMediaList(MediaList mediaList, Pageable pageable);
    Series findByMediaListAndIndex(MediaList mediaList, int index);
    Series save(Series series);
}
