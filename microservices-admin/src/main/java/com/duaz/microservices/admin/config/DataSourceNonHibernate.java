package com.duaz.microservices.admin.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySources({ 
	@PropertySource("classpath:application.properties") 
})
public class DataSourceNonHibernate {
    @Autowired
    private Environment env; 
 
	@Bean(name="dbNonHibernate.datasource")
    public DataSource dsNonHibernateDatasource() {
 
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db1.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("db1.datasource.url"));
        dataSource.setUsername(env.getProperty("db1.datasource.username"));
        dataSource.setPassword(env.getProperty("db1.datasource.password"));
 
        return dataSource;
    }
}
