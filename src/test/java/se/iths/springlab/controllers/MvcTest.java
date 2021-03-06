package se.iths.springlab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.services.Service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(MonkeyController.class)
@Import(TestService.class)
public class MvcTest {

    @Autowired
    //@MockBean
    Service service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Test
    void callingURLAnimalsReturnsAllMonkeysAsJson() throws Exception {

        // Mockito
        // when(service.getAllMonkeys()).thenReturn(List.of(new MonkeyDto(1, "T", "E", 1, "ST")));

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/animals")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
                //.andExpect(MockMvcResultMatchers.status().isOk())

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void callingPostWithMonkeyShouldSaveAndReturnNewMonkeyWithId() throws Exception {

        // when(service.createMonkey(any(MonkeyDto.class))).thenReturn(new MonkeyDto(1, "T", "E", 1, "ST"));

        var monkeyDto = new MonkeyDto(1, "T", "E", 1, "ST");
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsBytes(monkeyDto))
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(201);
    }
}
