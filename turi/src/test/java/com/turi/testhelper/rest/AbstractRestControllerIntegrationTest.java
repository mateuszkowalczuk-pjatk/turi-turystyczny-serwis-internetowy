package com.turi.testhelper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class AbstractRestControllerIntegrationTest
{
    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    protected HttpEntity<?> httpEntity;

    public AbstractRestControllerIntegrationTest()
    {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpEntity = new HttpEntity<>(headers);
    }

    public String getBaseUrl()
    {
       return "http://localhost:" + port;
    }
}
