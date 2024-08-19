package com.turi.testhelper.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.UUID;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig
{
    @Bean
    DataSource dataSource()
    {
        final var name = String.join(
                ";",
                UUID.randomUUID().toString(),
                "NON_KEYWORDS=VALUE",
                "DB_CLOSE_DELAY=-1",
                "DB_CLOSE_ON_EXIT=false",
                "MODE=PostgreSQL"
        );

        return new EmbeddedDatabaseBuilder()
                .setName(name)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .addScripts("classpath:/dbschema.sql", "classpath:/dbdata.sql")
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    EntityManagerFactory entityManagerFactory(final DataSource dataSource)
    {
        final var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final var em = new LocalContainerEntityManagerFactoryBean();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.turi");
        em.setDataSource(dataSource);
        em.afterPropertiesSet();

        return em.getObject();
    }
}
