package com.turi.testhelper.annotation;

import com.turi.testhelper.config.TestDataSourceConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DataJpaTest
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Import({TestDataSourceConfig.class})
@Sql({"/dbschema.sql", "/dbdata.sql"})
@ComponentScan({"com.turi.*.infrastructure.adapter.repository"})
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
public @interface RepositoryTest
{

}
