package com.github.hetikk.bootstrap.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hetikk.bootstrap.common.model.user.User;
import com.github.hetikk.bootstrap.controller.AbstractController;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Integer userId;

    @Test
    @SneakyThrows
    public void _1_create() {
        User user = new User();
        user.name = "Test user";
        user.email = "test@email.ru";
        user.password = "pa$$word";
        user.phone = "+7...";

        String body = objectMapper.writeValueAsString(user);
        String response = mockMvc.perform(post(USER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(user.name)))
                .andExpect(jsonPath("$.email", is(user.email)))
                .andExpect(jsonPath("$.password", is(user.password)))
                .andExpect(jsonPath("$.phone", is(user.phone)))
                .andExpect(jsonPath("$.roles", empty()))
                .andReturn().getResponse().getContentAsString();

        userId = JsonPath.parse(response).read("$.id", Integer.class);
    }

    @Test
    public void _2_getOne() throws Exception {
        mockMvc.perform(get(USER_URL + "/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    public void _3_getAll() throws Exception {
        mockMvc.perform(get(USER_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));
    }

    @Test
    @SneakyThrows
    public void _4_update() {
        User user = new User();
        user.name = "Test user NEW";

        String body = objectMapper.writeValueAsString(user);
        mockMvc.perform(patch(USER_URL + "/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(user.name)));
    }

    @Test
    public void _5_delete() throws Exception {
        mockMvc.perform(delete(USER_URL + "/" + userId))
                .andExpect(status().isNoContent());
    }

}
