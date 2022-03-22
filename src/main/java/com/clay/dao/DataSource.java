package com.clay.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Clay
 * @date
 */
public class DataSource {

    public static JdbcTemplate jdbcTemplate;

    public static void initDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        dataSource.setUsername("");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);

    }


}
