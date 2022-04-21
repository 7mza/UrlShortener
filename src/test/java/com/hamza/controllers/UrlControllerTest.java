package com.hamza.controllers;

import com.hamza.data.UrlEntity;
import com.hamza.exceptions.MalformedUrlException;
import com.hamza.exceptions.ResourceNotFoundException;
import com.hamza.exceptions.UnhandledException;
import com.hamza.sevices.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UrlControllerTest {

    @MockBean
    private UrlService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void create() throws Exception {
        when(service.create(anyString()))
                .thenReturn(new UrlEntity("1", "link1"));

        mvc.perform(post("/api/short_url/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullUrl\": \"link1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortUrl", is("1")))
                .andExpect(jsonPath("$.fullUrl", is("link1")));
    }

    @Test
    void createInvalidUrl() throws Exception {
        when(service.create(anyString()))
                .thenReturn(new UrlEntity("1", "link1"));

        mvc.perform(post("/api/short_url/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullUrl\": \"link1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortUrl", is("1")))
                .andExpect(jsonPath("$.fullUrl", is("link1")));
    }

    @Test
    @DisplayName("if service.create throws an UnhandledException, @controllerAdvice should transform it to error 500")
    void createUnhandledException() throws Exception {
        when(service.create(anyString()))
                .thenThrow(new UnhandledException("message"));

        mvc.perform(post("/api/short_url/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullUrl\": \"link1\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("001")))
                .andExpect(jsonPath("$.message", is("message")))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    @DisplayName("if service.create throws a MalformedUrlException, @controllerAdvice should transform it to error 400")
    void createMalformedUrlException() throws Exception {
        when(service.create(anyString()))
                .thenThrow(new MalformedUrlException());

        mvc.perform(post("/api/short_url/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullUrl\": \"link1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("003")))
                .andExpect(jsonPath("$.message", is("Malformed URL")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void read() throws Exception {
        when(service.read(anyString()))
                .thenReturn(new UrlEntity("1", "link1"));

        mvc.perform(get("/api/short_url/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortUrl", is("1")))
                .andExpect(jsonPath("$.fullUrl", is("link1")));
    }

    @Test
    @DisplayName("if service.read throws an UnhandledException, @controllerAdvice should transform it to error 500")
    void readUnhandledException() throws Exception {
        when(service.read(anyString()))
                .thenThrow(new UnhandledException("message"));

        mvc.perform(get("/api/short_url/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("001")))
                .andExpect(jsonPath("$.message", is("message")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("if service.read throws a ResourceNotFoundException, @controllerAdvice should transform it to error 400")
    void readResourceNotFoundException() throws Exception {
        when(service.read(anyString()))
                .thenThrow(new ResourceNotFoundException());

        mvc.perform(get("/api/short_url/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("002")))
                .andExpect(jsonPath("$.message", is("Resource not found")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void readAll() throws Exception {
        when(service.read())
                .thenReturn(
                        List.of(
                                new UrlEntity("1", "link1"),
                                new UrlEntity("2", "link2")
                        )
                );

        mvc.perform(get("/api/short_url/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].shortUrl", is("1")))
                .andExpect(jsonPath("$.content[0].fullUrl", is("link1"))).
                andExpect(jsonPath("$.content[1].shortUrl", is("2")))
                .andExpect(jsonPath("$.content[1].fullUrl", is("link2")));
    }

    @Test
    void update() throws Exception {
        when(service.update(anyString(), anyString()))
                .thenReturn(new UrlEntity("1", "link1"));

        mvc.perform(put("/api/short_url/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullUrl\": \"link1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortUrl", is("1")))
                .andExpect(jsonPath("$.fullUrl", is("link1")));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(service).delete(anyString());

        mvc.perform(MockMvcRequestBuilders.delete("/api/short_url/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }
}