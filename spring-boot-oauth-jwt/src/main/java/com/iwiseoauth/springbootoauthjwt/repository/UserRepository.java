package com.iwiseoauth.springbootoauthjwt.repository;

import com.iwiseoauth.springbootoauthjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */
@Repository
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String> {




}
