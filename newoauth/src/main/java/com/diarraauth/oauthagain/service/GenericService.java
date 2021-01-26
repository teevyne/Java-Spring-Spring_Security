package com.diarraauth.oauthagain.service;

import com.diarraauth.oauthagain.domain.RandomCity;
import com.diarraauth.oauthagain.domain.User;

import java.util.List;


public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<RandomCity> findAllRandomCities();
}
