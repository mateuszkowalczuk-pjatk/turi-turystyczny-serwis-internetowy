package com.turi.testhelper.annotation;

import com.turi.testhelper.config.RestControllerIntegrationTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Sql({"/dbschema.sql", "/dbdata.sql"})
@SpringBootTest(
        properties = {"spring.main.allow-bean-definition-overriding=true"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {RestControllerIntegrationTestConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class})
public @interface RestControllerTest
{

}
