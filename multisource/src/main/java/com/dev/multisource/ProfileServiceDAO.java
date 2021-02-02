package com.dev.multisource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileServiceDAO {

    @Qualifier("jdbcProfileService")
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int getCount_from_users(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }
}