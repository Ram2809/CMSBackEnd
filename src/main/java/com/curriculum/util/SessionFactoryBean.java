package com.curriculum.util;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.curriculum")
@EnableTransactionManagement
public class SessionFactoryBean {

	private DataSource dataSource;
	private Environment environment;

	public SessionFactoryBean(DataSource dataSource, Environment environment) {
		this.dataSource = dataSource;
		this.environment = environment;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionFactoryBuilder.scanPackages("com.curriculum.entity");
		sessionFactoryBuilder.addProperties(getHibernateProperties());
		return sessionFactoryBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

	/* Get Properties from Application.properties File and config into Hibernate */
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.default_schema",
				environment.getProperty("spring.jpa.properties.hibernate.default_schema"));
		properties.put("hibernate.connection.autocommit",
				environment.getProperty("spring.jpa.properties.hibernate.autocommit"));
		properties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));

		return properties;
	}
}
