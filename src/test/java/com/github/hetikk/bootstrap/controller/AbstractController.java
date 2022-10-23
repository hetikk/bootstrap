package com.github.hetikk.bootstrap.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = com.github.hetikk.bootstrap.Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("TEST")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public abstract class AbstractController {

    protected static final String USER_URL = "/users";

    @LocalServerPort
    protected Integer port;

}
