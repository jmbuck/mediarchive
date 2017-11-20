package com.mediarchive.server;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "media", path = "media")
public interface MediaRepository extends PagingAndSortingRepository<Media, Long> {

    List<Media> findByName(@Param("name") String name);

}