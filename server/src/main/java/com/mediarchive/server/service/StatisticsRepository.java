package com.mediarchive.server.service;

import com.mediarchive.server.domain.MediaList;
import com.mediarchive.server.domain.Statistics;
import org.springframework.data.repository.CrudRepository;

public interface StatisticsRepository extends CrudRepository<Statistics, Long> {
    Statistics findByMediaList(MediaList mediaList);
    Statistics findByMediaListAndIndex(MediaList mediaList, int index);
    <S extends Statistics>S save(S statistics);
}