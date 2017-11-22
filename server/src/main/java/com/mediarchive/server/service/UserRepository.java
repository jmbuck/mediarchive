package com.mediarchive.server.service;

import com.mediarchive.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    User findByName(String name);
}
