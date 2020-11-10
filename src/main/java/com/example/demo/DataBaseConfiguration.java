package com.example.demo;

import com.netflix.config.DynamicPropertyFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Slf4j
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.demo", entityManagerFactoryRef = "coreEntityManager", transactionManagerRef = "coreTransactionManager")
public class DataBaseConfiguration {

    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";
    @Value("${persistence.user:#{null}}")
    private String username;
    @Value("${persistence.password:#{null}}")
    private String password;
    @Value("${persistence.databaseName:#{null}}")
    private String database;
    @Value("${persistence.jdbcUrl:#{null}}")
    private String jdbcUrl;
    @Value("${persistence.serverName:#{null}}")
    private String server;
    @Value("${persistence.portNumber:#{null}}")
    private Integer port;
    @Value("${persistence.maximumPoolSize:#{null}}")
    private Integer maxPoolSize;
    @Value("${persistence.connectionTimeout:#{null}}")
    private Long connectionTimeout;

    private Boolean getShowSql() {
        return DynamicPropertyFactory.getInstance().getBooleanProperty(SHOW_SQL, false).getValue();
    }

    private Boolean getFormatSql() {
        return DynamicPropertyFactory.getInstance().getBooleanProperty(FORMAT_SQL, false).getValue();
    }

    @Bean
    @Primary
    @Qualifier("demoDataSource")
    public DataSource demoDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.addDataSourceProperty("databaseName", database);
        ds.addDataSourceProperty("serverName", server);
        ds.addDataSourceProperty("portNumber", port);
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setConnectionTimeout(connectionTimeout);
        ds.setMaximumPoolSize(maxPoolSize);
        return ds;
    }

    @Bean
    @Primary
    @Qualifier("coreEntityManager")
    public LocalContainerEntityManagerFactoryBean coreEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(demoDataSource());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.example.demo");
        em.setJpaPropertyMap(hibernateProperties());
        return em;
    }

    public HashMap<String, Object> hibernateProperties() {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put(Environment.SHOW_SQL, getShowSql());
        properties.put(Environment.FORMAT_SQL, getFormatSql());
        return properties;
    }

    @Bean
    @Primary
    @Qualifier("coreTransactionManager")
    public PlatformTransactionManager coreTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(coreEntityManager().getObject());
        return jpaTransactionManager;
    }
}

