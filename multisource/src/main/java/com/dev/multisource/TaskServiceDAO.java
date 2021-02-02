package com.dev.multisource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskServiceDAO {

    @Qualifier("jdbcTaskService")
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int getCount_from_tasks(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tasks", Integer.class);
    }
}
