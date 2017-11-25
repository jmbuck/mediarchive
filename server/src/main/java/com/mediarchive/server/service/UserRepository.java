package com.mediarchive.server.service;

import com.mediarchive.server.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

public interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    User findByName(String name);

}
