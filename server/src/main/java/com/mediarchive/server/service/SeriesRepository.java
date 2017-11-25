package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Series;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeriesRepository extends CrudRepository<Series, Long> {
    List<Series> findByMediaList(MediaList mediaList);
    Series findByMediaListAndIndex(MediaList mediaList, int index);
    <S extends Series>S save(S series);
}
