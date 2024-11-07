package com.turi.testhelper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class AbstractRestControllerIntegrationTest
{
    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    protected HttpHeaders headers;

    public AbstractRestControllerIntegrationTest()
    {
        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    public String getBaseUrl()
    {
       return "http://localhost:" + port;
    }
}
