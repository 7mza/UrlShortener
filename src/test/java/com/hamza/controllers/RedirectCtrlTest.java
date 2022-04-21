package com.hamza.controllers;

import com.hamza.data.UrlEntity;
import com.hamza.sevices.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RedirectCtrlTest {

    @MockBean
    private UrlService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void redirect() throws Exception {
        when(service.read(anyString()))
                .thenReturn(new UrlEntity("1", "google.fr"));

        mvc.perform(get("/short_url/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.header().stringValues("Location", "https://google.fr"));
    }
}