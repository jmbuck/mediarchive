package com.mediarchive.server.service;

import com.mediarchive.server.domain.Book;
import com.mediarchive.server.domain.MediaList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Page<Book> findByMediaList(MediaList mediaList, Pageable pageable);
    Book findByMediaListAndIndex(MediaList mediaList, int index);
    Book save(Book movie);
}
