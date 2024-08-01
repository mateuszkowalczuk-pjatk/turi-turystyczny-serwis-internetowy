package com.turi.testhelper.annotation;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DataJpaTest
@Retention(RetentionPolicy.RUNTIME)
@Sql({"/dbschema.sql", "/dbdata.sql"})
@ComponentScan({"com.turi.*.infrastructure.adapter.repository"})
public @interface ServiceTest
{

}

