package com.dev.multisource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean(name = "dbprofileservice")
    @ConfigurationProperties(prefix = "spring.dbprofileservice")
    public DataSource createProfileDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbtaskservice")
    @ConfigurationProperties(prefix = "spring.dbtaskservice")
    public DataSource createTaskServiceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcProfileService")
    @Autowired
    public JdbcTemplate createJdbcTemplate_ProfileService(@Qualifier("dbprofileservice") DataSource profileServiceDS) {
        return new JdbcTemplate(profileServiceDS);
    }

    @Bean(name = "jdbcTaskService")
    @Autowired
    public JdbcTemplate createJdbcTemplate_TaskService(@Qualifier("dbtaskservice") DataSource taskServiceDS) {
        return new JdbcTemplate(taskServiceDS);
    }
}
