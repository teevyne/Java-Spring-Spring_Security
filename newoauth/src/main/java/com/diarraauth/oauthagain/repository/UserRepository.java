package com.diarraauth.oauthagain.repository;

import com.diarraauth.oauthagain.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by teevyne on 06/05/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
