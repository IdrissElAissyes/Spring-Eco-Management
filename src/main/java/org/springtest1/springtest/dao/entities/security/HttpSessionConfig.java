//package org.springtest1.springtest.dao.entities.security;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
//import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJdbcHttpSession
//public class HttpSessionConfig {
//
//    @Bean
//    @Primary
//    public JdbcHttpSessionConfiguration jdbcHttpSessionConfiguration() {
//        return new JdbcHttpSessionConfiguration();
//    }
//
//
//    @Bean
//    public DataSource dataSource() {
//        // Configure your data source here
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:3306/prd-ddb?createDatabaseIfNotExist=true")
//                .username("root")
//                .password("")
//                .build();
//    }
//}