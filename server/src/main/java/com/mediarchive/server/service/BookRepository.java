package com.mediarchive.server.service;

import com.mediarchive.server.domain.Book;
import com.mediarchive.server.domain.MediaList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByMediaList(MediaList mediaList);
    Book findByMediaListAndIndex(MediaList mediaList, int index);
    Book findByMediaListAndId(MediaList mediaList, String id);
    <S extends Book>S save(S book);
}
