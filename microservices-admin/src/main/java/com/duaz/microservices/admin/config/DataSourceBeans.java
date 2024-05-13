package com.duaz.microservices.admin.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@Configuration
public class DataSourceBeans {
	
	@Primary
	@Bean(name="db1.datasource")
	@ConfigurationProperties(prefix="db1.datasource")
	public DataSource db1DataSource(){
		System.out.println("data1DataSource");
		return DataSourceBuilder.create().build();
	}
	
	/*@Bean(name="db2.datasource")
	@ConfigurationProperties(prefix="db2.datasource")
	public DataSource db2DataSource(){
		System.out.println("data2DataSource");
		return DataSourceBuilder.create().build();
	}*/
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}
}
